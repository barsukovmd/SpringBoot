package com.teachmeskills.springbootexample.services;

import com.teachmeskills.springbootexample.entities.User;
import com.teachmeskills.springbootexample.exceptions.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView authenticate(User user) throws AuthorizationException;

}
