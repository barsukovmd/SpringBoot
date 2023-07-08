package com.teachmeskills.springboot.estore.services;

import com.teachmeskills.springboot.estore.entities.User;
import com.teachmeskills.springboot.estore.exceptions.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    ModelAndView authenticate(User user) throws AuthorizationException;

}
