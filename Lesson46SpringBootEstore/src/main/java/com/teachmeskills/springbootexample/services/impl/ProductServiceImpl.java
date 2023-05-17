package com.teachmeskills.springbootexample.services.impl;

import com.teachmeskills.springbootexample.PagesPathEnum;
import com.teachmeskills.springbootexample.RequestParamsEnum;
import com.teachmeskills.springbootexample.entities.Product;
import com.teachmeskills.springbootexample.repositories.ProductRepository;
import com.teachmeskills.springbootexample.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product entity) {
        return null;
    }

    @Override
    public List<Product> read() {
        return null;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Product> getAllForCategory(int categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public ModelAndView getProductData(int id) {
        ModelMap model = new ModelMap();
        Product product = productRepository.findById(id);
        if (Optional.ofNullable(product).isPresent()) {
            model.addAttribute(RequestParamsEnum.PRODUCT.getValue(), product);
        }
        return new ModelAndView(PagesPathEnum.PRODUCT_PAGE.getPath(), model);
    }
}
