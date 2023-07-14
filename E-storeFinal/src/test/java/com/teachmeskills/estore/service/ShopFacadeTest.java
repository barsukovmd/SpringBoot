package com.teachmeskills.estore.service;

import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.dto.CartDto;
import com.teachmeskills.estore.dto.ProductDto;
import com.teachmeskills.estore.dto.RoleDto;
import com.teachmeskills.estore.dto.UserFormDto;
import com.teachmeskills.estore.mapper.UserMapper;
import com.teachmeskills.estore.model.Location;
import com.teachmeskills.estore.security.CustomUserDetail;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.teachmeskills.estore.test_utils.Constants.LOCATION;
import static com.teachmeskills.estore.test_utils.Constants.*;
import static com.teachmeskills.estore.test_utils.Constants.PAGE;
import static com.teachmeskills.estore.test_utils.Constants.PRODUCT_ID;
import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.USER_ID;
import static com.teachmeskills.estore.utils.Constants.*;
import static com.teachmeskills.estore.utils.Constants.Attributes.*;
import static com.teachmeskills.estore.utils.Constants.ControllerMappingPath.ADMIN_INFO;
import static com.teachmeskills.estore.utils.Constants.MappingPath.PRODUCTS;
import static com.teachmeskills.estore.utils.Constants.MappingPath.*;
import static com.teachmeskills.estore.utils.Constants.RequestParameters.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShopFacadeTest {

    @Autowired
    private ShopFacade shopFacade;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductCategoryService productCategoryService;

    @MockBean
    private CartService cartService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private CustomUserDetail customUserDetail;

    @MockBean
    private User userMock;

    private final User user = User.builder().build();
    private final UserFormDto userFormDto = UserFormDto.builder().build();
    private ModelAndView modelAndView = new ModelAndView();

    private static ProductDto getProductDto(Long id) {
        return ProductDto.builder()
                .id(id)
                .build();
    }

    @Test
    void test_getFavoriteProducts() {
        Long userId = 4L;
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .price(BigDecimal.TEN)
                .name("someName")
                .info("someInfo")
                .category(PRODUCT_CATEGORY)
                .build();
        List<CartDto> cartDtos = List.of(CartDto.builder()
                .id(2L)
                .productDto(productDto)
                .cart(false)
                .favorite(true)
                .count(3)
                .build());

        when(cartService.getSelectedProducts(userId, Location.FAVORITE)).thenReturn(cartDtos);

        assertEquals(List.of(productDto), shopFacade.getFavoriteProducts(userId));
        verify(cartService, atLeastOnce()).getSelectedProducts(any(), any());
    }

    @Test
    void test_createUser_setRoleAndPassword() {
        var captor = ArgumentCaptor.forClass(UserFormDto.class);
        userFormDto.setPassword("qwerty");

        when(passwordEncoder.encode(userFormDto.getPassword())).thenReturn(SECRET_QWERTY);
        when(roleService.getRole(ROLE_USER)).thenReturn(RoleDto.builder().role(ROLE_USER).build());
        when(userMapper.convertToUser(userFormDto)).thenReturn(user);
        doNothing().when(userService).addUser(user);

        shopFacade.createUser(userFormDto);

        verify(userMapper).convertToUser(captor.capture());
        UserFormDto value = captor.getValue();
        assertEquals(ROLE_USER, value.getRoleDto().getRole());
        assertEquals(SECRET_QWERTY, value.getPassword());
    }

    @Test
    public void test_EditUser_setNameAndSurname() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetail);
        when(customUserDetail.getUser()).thenReturn(userMock);

        shopFacade.editUser(userFormDto);

        verify(userMock).setName(userFormDto.getName());
        verify(userMock).setSurname(userFormDto.getSurname());
        verify(userService).addUser(userMock);

        SecurityContextHolder.clearContext();
    }

    @Test
    void test_carriesPurchase() {
        List<ProductDto> productsDto = List.of(ProductDto.builder().build());
        Long userId = 1L;
        Location location = Location.CART;

        when(cartService.getPurchasedProducts(userId, location)).thenReturn(productsDto);
        doNothing().when(orderService).saveUserOrder(userId, productsDto);
        doNothing().when(cartService).deleteCartProductsAfterBuy(userId);

        shopFacade.carriesPurchase(userId);

        verify(cartService, atLeastOnce()).getPurchasedProducts(userId, location);
        verify(orderService, atLeastOnce()).saveUserOrder(userId, productsDto);
        verify(cartService, atLeastOnce()).deleteCartProductsAfterBuy(userId);
    }

    @Test
    void test_getModelAndViewByParams_setViewName() {
        Long productId = PRODUCT_ID;
        String category = PRODUCT_CATEGORY;
        String currentLocation = SEARCH;
        Integer page = null;

        when(productService.getProductCategoryValue(any())).thenReturn(category);

        shopFacade.getPathFromAddFavoriteByParameters(productId, currentLocation, category, page);

        modelAndView = shopFacade.getModelAndViewByParams(productId, currentLocation, page);
        assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, modelAndView.getViewName());
    }

    @Test
    void test_getEshopView() {
        List<String> productCategories = List.of("tv", "phone");

        when(productCategoryService.getProductCategories()).thenReturn(productCategories);

        shopFacade.getEshopView(modelAndView);

        assertEquals(productCategories, modelAndView.getModel().get(PRODUCT_CATEGORIES));
        assertEquals(ESHOP, modelAndView.getViewName());
    }

    @Test
    void test_getAdminPage() {
        Long productOneId = 1L;
        Long productOneCount = 3L;
        Map<Long, Long> mostFavoriteOne = Map.of(productOneId, productOneCount);
        List<Map<Long, Long>> mostFavorites = List.of(mostFavoriteOne);
        ProductDto productDtoOne = getProductDto(productOneId);
        List<Map<ProductDto, Long>> productsWithCount = List.of(Map.of(productDtoOne, productOneCount));

        when(cartService.getMostFavorite()).thenReturn(mostFavorites);
        when(productService.getProductDto(any())).thenReturn(productDtoOne);
        when(productService.getCount(any())).thenReturn(productOneCount);

        shopFacade.getAdminPage(modelAndView);

        assertEquals(productsWithCount, modelAndView.getModel().get(PRODUCTS));
        assertEquals(ADMIN_INFO, modelAndView.getViewName());
    }

    @Nested
    class TestGetPathFromAddCart {

        private final String shopFlagElse = "someFlag";
        private final String shopFlagTrue = TRUE;

        @Test
        void test_getPathFromAddCartByParameters_toCart_pageNull() {
            assertEquals(REDIRECT_TO_CART, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagTrue, LOCATION, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toCart_pageNotNull() {
            assertEquals(REDIRECT_TO_CART + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagTrue, LOCATION, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toFavorites_pageNull() {
            assertEquals(REDIRECT_TO_FAVORITES, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, FAVORITE, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toFavorites_pageNotNull() {
            assertEquals(REDIRECT_TO_FAVORITES + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, FAVORITE, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toSearch_pageNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, SEARCH, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toSearch_pageNotNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, SEARCH, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toProduct_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCT + PRODUCT_ID, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, PRODUCT_PAGE, null));
        }

        @Test
        void test_getPathFromAddCartByParameters_toProduct_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCT + PRODUCT_ID + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, PRODUCT_PAGE, PAGE));
        }

        @Test
        void test_getPathFromAddCartByParameters_toProducts_pageNull() {
            when(productService.getProductCategoryValue(PRODUCT_ID)).thenReturn(PRODUCT_CATEGORY);

            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, LOCATION, null));
            verify(productService, atLeastOnce()).getProductCategoryValue(any());
        }

        @Test
        void test_getPathFromAddCartByParameters_toProducts_pageNotNull() {
            when(productService.getProductCategoryValue(PRODUCT_ID)).thenReturn(PRODUCT_CATEGORY);

            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE + EXTENSION_PATH, shopFacade.getPathFromAddCartByParameters(PRODUCT_ID, shopFlagElse, LOCATION, PAGE));
            verify(productService, atLeastOnce()).getProductCategoryValue(any());
        }
    }

    @Nested
    class TestGetPathFromAddFavorite {

        @Test
        void test_getPathFromAddFavoriteByParameters_toSearch_pageNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toSearch_pageNotNull() {
            assertEquals(REDIRECT_TO_SEARCH_RESULT_SAVE + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, SEARCH, PRODUCT_CATEGORY, PAGE));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProduct_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCT + PRODUCT_ID, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProduct_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCT + PRODUCT_ID + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, PRODUCT_PAGE, PRODUCT_CATEGORY, PAGE));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProducts_pageNull() {
            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, null));
        }

        @Test
        void test_getPathFromAddFavoriteByParameters_toProducts_pageNotNull() {
            assertEquals(REDIRECT_TO_PRODUCTS_PAGE_CATEGORY_WITH_PARAM + PRODUCT_CATEGORY + AND_SIZE + PRODUCT_PAGE_SIZE + EXTENSION_PATH, shopFacade.getPathFromAddFavoriteByParameters(PRODUCT_ID, LOCATION, PRODUCT_CATEGORY, PAGE));
        }
    }

    @Nested
    class TestGetPageByParam {

        @Test
        void test_getPageByParam_paramBuy() {
            SecurityContextHolder.setContext(securityContext);

            String param = BUY;

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(customUserDetail);
            when(customUserDetail.getUser()).thenReturn(userMock);

            modelAndView = shopFacade.getPageByParam(param);

            assertEquals(SUCCESS_BUY, modelAndView.getViewName());

            SecurityContextHolder.clearContext();
        }

        @Test
        void test_getPageByParam_paramNotBuy() {
            SecurityContextHolder.setContext(securityContext);

            String param = "any";

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(customUserDetail);
            when(customUserDetail.getUser()).thenReturn(userMock);

            modelAndView = shopFacade.getPageByParam(param);

            assertEquals(REDIRECT_TO_CART, modelAndView.getViewName());

            SecurityContextHolder.clearContext();
        }
    }

    @Nested
    class TestGetUserEditForm {

        @Test
        void test_getUserEditForm_userIsPresent() {
            Long userId = USER_ID;
            user.setId(userId);
            user.setName("name");
            userFormDto.setId(userId);
            userFormDto.setName(user.getName());

            when(userService.getUserById(userId)).thenReturn(Optional.of(user));
            when(userMapper.convertToUserFormDto(user)).thenReturn(userFormDto);

            ModelAndView result = shopFacade.getUserEditForm(userId);

            assertEquals(userFormDto, result.getModel().get(USER));
            assertEquals(EDIT, result.getViewName());
        }

        @Test
        void test_getUserEditForm_userIsNotPresent() {
            Long userId = USER_ID;

            when(userService.getUserById(userId)).thenReturn(Optional.empty());

            ModelAndView result = shopFacade.getUserEditForm(userId);

            assertEquals(ACCOUNT, result.getViewName());
        }
    }

    @Nested
    class TestSetPriceAndRedirectAttributes {

        private final RedirectAttributes attr = new RedirectAttributesModelMap();

        @Test
        void test_setPriceAndRedirectAttributes_validPrice() {
            ProductDto productDto = ProductDto.builder()
                    .price(BigDecimal.TEN)
                    .build();

            doNothing().when(productService).changePrice(productDto);

            shopFacade.setPriceAndRedirectAttributes(productDto, attr);

            assertTrue(attr.getFlashAttributes().containsKey(SUCCESS));
            verify(productService, atLeastOnce()).changePrice(productDto);
        }

        @Test
        void test_setPriceAndRedirectAttributes_invalidPrice() {
            ProductDto productDto = ProductDto.builder()
                    .price(BigDecimal.ZERO)
                    .build();

            doNothing().when(productService).changePrice(productDto);

            shopFacade.setPriceAndRedirectAttributes(productDto, attr);

            assertTrue(attr.getFlashAttributes().containsKey(ERROR));
            verify(productService, never()).changePrice(productDto);
        }
    }
}
