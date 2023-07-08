package com.teachmeskills.springboot.estore.controllers;

import com.teachmeskills.springboot.estore.EshopConstants;
import com.teachmeskills.springboot.estore.PagesPathEnum;
import com.teachmeskills.springboot.estore.entities.User;
import com.teachmeskills.springboot.estore.exceptions.AuthorizationException;
import com.teachmeskills.springboot.estore.services.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@RestController
@SessionAttributes({EshopConstants.USER})
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openLoginPage() {
        return new ModelAndView(PagesPathEnum.SIGN_IN_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView login(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, ModelAndView modelAndView) throws AuthorizationException, AuthorizationException {
        if (bindingResult.hasErrors()) {
            populateError("email", modelAndView, bindingResult);
            populateError("password", modelAndView, bindingResult);
            modelAndView.setViewName(PagesPathEnum.SIGN_IN_PAGE.getPath());
            return modelAndView;
        }

        return userService.authenticate(user);
    }

    @ModelAttribute(EshopConstants.USER)
    public User setUpUserForm() {
        return new User();
    }

    private void populateError(String field, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors(field)) {
            modelAndView.addObject(field + "Error", Objects.requireNonNull(bindingResult.getFieldError(field))
                    .getDefaultMessage());
        }
    }
}
