package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.dto.ProductDto;
import com.teachmeskills.estore.service.ShopFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.teachmeskills.estore.utils.Constants.Attributes.PRODUCT;
import static com.teachmeskills.estore.utils.Constants.MappingPath.REDIRECT_TO_ADMIN;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ShopFacade shopFacade;

    @GetMapping
    public ModelAndView showAdminPage(ModelAndView modelAndView) {
        return shopFacade.getAdminPage(modelAndView);
    }

    @PostMapping("/price")
    public ModelAndView changePrice(@ModelAttribute(name = PRODUCT) ProductDto product,
                                    RedirectAttributes attr) {
        shopFacade.setPriceAndRedirectAttributes(product, attr);
        return new ModelAndView(REDIRECT_TO_ADMIN);
    }
}
