package com.teachmeskills.springboot.estore.services;

import com.teachmeskills.springboot.estore.entities.Category;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category> {
    ModelAndView getCategoryData(int id);
}
