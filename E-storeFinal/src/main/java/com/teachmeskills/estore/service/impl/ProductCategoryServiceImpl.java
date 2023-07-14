package com.teachmeskills.estore.service.impl;

import com.teachmeskills.estore.repository.ProductCategoryRepository;
import com.teachmeskills.estore.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<String> getProductCategories() {
        return productCategoryRepository.findAllCategory();
    }
}
