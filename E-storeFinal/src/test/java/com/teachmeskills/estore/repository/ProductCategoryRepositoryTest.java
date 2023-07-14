package com.teachmeskills.estore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static com.teachmeskills.estore.test_utils.Constants.TEST_PROPERTY_SOURCE_LOCATIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = TEST_PROPERTY_SOURCE_LOCATIONS)
@Sql(value = "classpath:sql/product_category/product-category-com.teachmeskills.estore.repository-before.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:sql/product_category/product-category-com.teachmeskills.estore.repository-after.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void test_findAllCategory() {
        int expectedSize = 2;

        List<String> categories = productCategoryRepository.findAllCategory();

        assertFalse(categories.isEmpty());
        assertEquals(expectedSize, categories.size());
    }
}
