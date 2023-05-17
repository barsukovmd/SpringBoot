package com.teachmeskills.springbootexample.services;

import com.teachmeskills.springbootexample.entities.Product;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getAllForCategory(int categoryId);

    ModelAndView getProductData(int id);
}
