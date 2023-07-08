package com.teachmeskills.springboot.estore.controllers;

import com.teachmeskills.springboot.estore.EshopConstants;
import com.teachmeskills.springboot.estore.PagesPathEnum;
import com.teachmeskills.springboot.estore.entities.Cart;
import com.teachmeskills.springboot.estore.services.CartService;
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
