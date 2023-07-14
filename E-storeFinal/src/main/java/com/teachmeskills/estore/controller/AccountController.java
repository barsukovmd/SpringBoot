package com.teachmeskills.estore.controller;

import com.teachmeskills.estore.dto.OrderDto;
import com.teachmeskills.estore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.teachmeskills.estore.utils.Constants.Attributes.USER_ORDER;
import static com.teachmeskills.estore.utils.Constants.MappingPath.ACCOUNT;
import static com.teachmeskills.estore.utils.ControllerUtils.getAuthenticationUserId;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final OrderService orderService;

    @GetMapping("/account")
    public ModelAndView showAccountPage(ModelAndView modelAndView) {
        List<OrderDto> orders = orderService.getOrdersById(getAuthenticationUserId());
        modelAndView.addObject(USER_ORDER, orders);
        modelAndView.setViewName(ACCOUNT);
        return modelAndView;
    }
}
