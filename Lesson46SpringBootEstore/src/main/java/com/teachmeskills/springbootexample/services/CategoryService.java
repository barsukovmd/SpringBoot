package com.teachmeskills.springbootexample.services;

import com.teachmeskills.springbootexample.entities.Category;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category> {
    ModelAndView getCategoryData(int id);
}
