package com.teachmeskills.estore.service.impl;

import com.teachmeskills.estore.repository.ProductCategoryRepository;
import com.teachmeskills.estore.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.teachmeskills.estore.test_utils.Constants.PHONE;
import static com.teachmeskills.estore.test_utils.Constants.TV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void test_getProductCategories() {
        List<String> categories = List.of(TV, PHONE);

        when(productCategoryRepository.findAllCategory()).thenReturn(categories);

        List<String> foundCategories = productCategoryService.getProductCategories();

        assertEquals(categories, foundCategories);
        verify(productCategoryRepository, atLeastOnce()).findAllCategory();
    }
}
