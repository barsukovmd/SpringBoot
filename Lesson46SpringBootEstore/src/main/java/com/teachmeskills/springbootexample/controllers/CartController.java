package com.teachmeskills.springbootexample.controllers;

import com.teachmeskills.springbootexample.EshopConstants;
import com.teachmeskills.springbootexample.PagesPathEnum;
import com.teachmeskills.springbootexample.entities.Cart;
import com.teachmeskills.springbootexample.services.CartService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@SessionAttributes({EshopConstants.SHOPPING_CART})
@RequestMapping("/cart")
public class CartController {
    private static final String SHOPPING_CART = "cart";
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/add")
    public ModelAndView addProductToCart(@RequestParam(EshopConstants.PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.addProductToCart(productId, shopCart);
    }

    @GetMapping("/open")
    public ModelAndView redirectToShoppingCart() {
        return new ModelAndView(PagesPathEnum.CART_PAGE.getPath());
    }

    @ModelAttribute(SHOPPING_CART)
    public Cart shoppingCart() {
        return new Cart();
    }
}
