package com.teachmeskills.springboot.estore.services;

import com.teachmeskills.springboot.estore.entities.Product;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<Product> getAllForCategory(int categoryId);

    ModelAndView getProductData(int id);
}
