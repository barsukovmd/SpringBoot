package com.tms.estore.service;

import com.tms.estore.domain.User;
import com.tms.estore.dto.CartDto;
import com.tms.estore.dto.ProductDto;
import com.tms.estore.dto.RoleDto;
import com.tms.estore.dto.UserFormDto;
import com.tms.estore.mapper.UserMapper;
import com.tms.estore.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tms.estore.utils.Constants.*;
import static com.tms.estore.utils.Constants.Attributes.*;
import static com.tms.estore.utils.Constants.ControllerMappingPath.ADMIN_INFO;
import static com.tms.estore.utils.Constants.MappingPath.*;
import static com.tms.estore.utils.Constants.RequestParameters.*;
import static com.tms.estore.utils.ControllerUtils.*;

@Component
@RequiredArgsConstructor
public class ShopFacade {

    private final CartService cartService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ProductCategoryService productCategoryService;

    public void carriesPurchase(Long userId) {
        List<ProductDto> productsDto = cartService.getPurchasedProducts(userId, Location.CART);
        orderService.saveUserOrder(userId, productsDto);
        cartService.deleteCartProductsAfterBuy(userId);
    }

    public String getPathFromAddCartByParameters(Long productId, String shopFlag, String location, Integer page) {
        String path;
        if (Objects.equals(shopFlag, TRUE)) {
            path = REDIRECT_TO_CART;
        } else if (Objects.equals(location, FAVORITE)) {
            path = REDIRECT_TO_FAVORITES;
        } else if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT + productId;
        } else {
            String productCategory = productService.getProductCategoryValue(productId);
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + PRODUCT_PAGE_SIZE;
        }
        return getPathByPagination(page, path);
    }

    public String getPathFromAddFavoriteByParameters(Long productId, String location, String productCategory, Integer page) {
        String path;
        if (Objects.equals(location, SEARCH)) {
            path = REDIRECT_TO_SEARCH_RESULT_SAVE;
        } else if (Objects.equals(location, PRODUCT_PAGE)) {
            path = REDIRECT_TO_PRODUCT + productId;
        } else {
            path = REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + productCategory + AND_SIZE + PRODUCT_PAGE_SIZE;
        }
        return getPathByPagination(page, path);
    }

    public ModelAndView getModelAndViewByParams(Long productId, String location, Integer page) {
        return new ModelAndView(getPathFromAddFavoriteByParameters(productId, location, productService.getProductCategoryValue(productId), page));
    }

    public ModelAndView getPageByParam(String param) {
        ModelAndView modelAndView = new ModelAndView();
        if (param.equalsIgnoreCase(BUY)) {
            carriesPurchase(getAuthenticationUserId());
            modelAndView.setViewName(SUCCESS_BUY);
        } else {
            modelAndView.setViewName(REDIRECT_TO_CART);
        }
        return modelAndView;
    }

    public void getEshopView(ModelAndView modelAndView) {
        List<String> productCategories = productCategoryService.getProductCategories();
        modelAndView.addObject(PRODUCT_CATEGORIES, productCategories);
        modelAndView.setViewName(ESHOP);
    }

    public void createUser(UserFormDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleDto roleUser = roleService.getRole("ROLE_USER");
        user.setRoleDto(roleUser);
        User userEntity = userMapper.convertToUser(user);
        userService.addUser(userEntity);
    }

    public void editUser(UserFormDto user) {
        User authenticationUser = getAuthenticationUser();
        authenticationUser.setName(user.getName());
        authenticationUser.setSurname(user.getSurname());
        userService.addUser(authenticationUser);
    }

    public List<ProductDto> getFavoriteProducts(Long id) {
        List<CartDto> cartsDto = cartService.getSelectedProducts(id, Location.FAVORITE);
        return cartsDto.stream()
                .map(CartDto::getProductDto)
                .collect(Collectors.toList());
    }

    public ModelAndView getUserEditForm(Long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            UserFormDto userFormDto = userMapper.convertToUserFormDto(user);
            modelAndView.addObject(USER, userFormDto);
            modelAndView.setViewName(EDIT);
        } else {
            modelAndView.setViewName(ACCOUNT);
        }
        return modelAndView;
    }

    public ModelAndView getAdminPage(ModelAndView modelAndView) {
        List<Map<Long, Long>> mostFavorites = cartService.getMostFavorite();
        List<Map<ProductDto, Long>> productsWithCount = mostFavorites.stream()
                .map(mostFavorite -> {
                    Map<ProductDto, Long> productWithCount = new HashMap<>();
                    productWithCount.put(productService.getProductDto(mostFavorite.get(PRODUCT_ID)), productService.getCount(mostFavorite.get(COUNT)));
                    return productWithCount;
                })
                .toList();
        modelAndView.addObject(Attributes.PRODUCTS, productsWithCount);
        modelAndView.setViewName(ADMIN_INFO);
        return modelAndView;
    }

    public void setPriceAndRedirectAttributes(ProductDto product, RedirectAttributes attr) {
        boolean isValidPrice = isValidPrice(product);
        addRedirectAttribute(attr, isValidPrice);
        changePriceIfValid(product, isValidPrice);
    }

    private void changePriceIfValid(ProductDto product, boolean isValidPrice) {
        if (isValidPrice) {
            productService.changePrice(product);
        }
    }

    private void addRedirectAttribute(RedirectAttributes attr, boolean condition) {
        if (condition) {
            attr.addFlashAttribute(SUCCESS, true);
        } else {
            attr.addFlashAttribute(ERROR, true);
        }
    }

    private boolean isValidPrice(ProductDto product) {
        return product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
}
