package com.teachmeskills.springboot.estore.services.impl;

import com.teachmeskills.springboot.estore.PagesPathEnum;
import com.teachmeskills.springboot.estore.RequestParamsEnum;
import com.teachmeskills.springboot.estore.entities.Category;
import com.teachmeskills.springboot.estore.entities.User;
import com.teachmeskills.springboot.estore.exceptions.AuthorizationException;
import com.teachmeskills.springboot.estore.repositories.UserRepository;
import com.teachmeskills.springboot.estore.services.CategoryService;
import com.teachmeskills.springboot.estore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public ModelAndView authenticate(User user) throws AuthorizationException {
        log.info("Authentication method call");
        ModelAndView modelAndView = new ModelAndView();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                ModelMap modelMap = new ModelMap();
                List<Category> categoriesList = categoryService.read();
                modelMap.addAttribute(RequestParamsEnum.POPULAR_CATEGORIES_LIST_REQ_PARAM.getValue(), categoriesList);
                modelAndView.setViewName(PagesPathEnum.START_PAGE.getPath());
                modelAndView.addAllObjects(modelMap);
            } else {
                throw new AuthorizationException("User is not authorized! Please, try again.");
            }
        }
        return modelAndView;
    }
}
