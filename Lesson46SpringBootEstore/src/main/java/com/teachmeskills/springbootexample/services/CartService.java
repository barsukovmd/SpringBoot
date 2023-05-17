package com.teachmeskills.springbootexample.services;

import com.teachmeskills.springbootexample.EshopConstants;
import com.teachmeskills.springbootexample.PagesPathEnum;
import com.teachmeskills.springbootexample.RequestParamsEnum;
import com.teachmeskills.springbootexample.entities.Cart;
import com.teachmeskills.springbootexample.entities.Product;
import com.teachmeskills.springbootexample.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


@Service
public class CartService {
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ModelAndView addProductToCart(int productId, Cart shopCart) {
        ModelMap modelParams = new ModelMap();

        Product product = productRepository.findById(productId);
        shopCart.addProduct(product);

        modelParams.addAttribute(RequestParamsEnum.PRODUCT.getValue(), product);
        modelParams.addAttribute(EshopConstants.SHOPPING_CART, shopCart);

        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath(), modelParams);
    }
}
