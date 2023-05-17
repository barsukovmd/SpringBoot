package com.teachmeskills.springbootexample.controllers;

import com.teachmeskills.springbootexample.PagesPathEnum;
import com.teachmeskills.springbootexample.RequestParamsEnum;
import com.teachmeskills.springbootexample.entities.Category;
import com.teachmeskills.springbootexample.services.CategoryService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ModelAndView getHomePage(/*@SessionAttribute(USER) User user*/) {
//        System.out.println("User with email: " + user.getEmail() + " and password: " + user.getPassword() + " successfully logged in!");
        ModelMap model = new ModelMap();

        List<Category> categoriesList = categoryService.read();

        model.addAttribute(RequestParamsEnum.POPULAR_CATEGORIES_LIST_REQ_PARAM.getValue(), categoriesList);

        return new ModelAndView(PagesPathEnum.START_PAGE.getPath(), model);
    }

}
