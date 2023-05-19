package com.teachmeskills.springboot.estore.services;

import com.teachmeskills.springboot.estore.EshopConstants;
import com.teachmeskills.springboot.estore.PagesPathEnum;
import com.teachmeskills.springboot.estore.RequestParamsEnum;
import com.teachmeskills.springboot.estore.entities.Cart;
import com.teachmeskills.springboot.estore.entities.Product;
import com.teachmeskills.springboot.estore.repositories.ProductRepository;
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
