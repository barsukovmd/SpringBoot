package com.teachmeskills.springbootexample.services.impl;

import com.teachmeskills.springbootexample.PagesPathEnum;
import com.teachmeskills.springbootexample.RequestParamsEnum;
import com.teachmeskills.springbootexample.entities.Category;
import com.teachmeskills.springbootexample.entities.Product;
import com.teachmeskills.springbootexample.repositories.CategoryRepository;
import com.teachmeskills.springbootexample.services.CategoryService;
import com.teachmeskills.springbootexample.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    public Category create(Category entity) {
        return null;
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAllCategories();
    }

    @Override
    public Category update(Category entity) {
        return null;
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public ModelAndView getCategoryData(int id) {
        ModelMap model = new ModelMap();

//        List<Category> categoryList = categoryRepository.findAllByRating(1, PageRequest.of(0, 10));
//        Page<Category> all = categoryRepository.findAll(PageRequest.of(0, 10));

        Category category = categoryRepository.findById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.getAllForCategory(category.getId());
            category.setProductList(products);
            model.addAttribute(RequestParamsEnum.CATEGORY_PARAM.getValue(), category);
        }

        return new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath(), model);
    }
}
