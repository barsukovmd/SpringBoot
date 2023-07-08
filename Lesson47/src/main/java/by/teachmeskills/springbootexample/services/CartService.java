package by.teachmeskills.springbootexample.services;

import by.teachmeskills.springbootexample.entities.Cart;
import by.teachmeskills.springbootexample.entities.Product;
import by.teachmeskills.springbootexample.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static by.teachmeskills.springbootexample.EshopConstants.SHOPPING_CART;
import static by.teachmeskills.springbootexample.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootexample.RequestParamsEnum.PRODUCT;


@Service
public class CartService {
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();

        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(shopCart::addProduct);

        modelParams.addAttribute(PRODUCT.getValue(), product);
        modelParams.addAttribute(SHOPPING_CART, shopCart);

        return new ModelAndView(CART_PAGE.getPath(), modelParams);
    }
}
