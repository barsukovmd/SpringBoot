package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Product;
import com.teachmeskills.estore.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.PRODUCT;
import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.PRODUCT_DTO;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void test_convertToProductDto() {
        ProductDto convertedProductDto = productMapper.convertToProductDto(PRODUCT);

        assertEquals(PRODUCT_DTO, convertedProductDto);
    }

    @Test
    void test_convertToProduct() {
        Product convertedProduct = productMapper.convertToProduct(PRODUCT_DTO);

        assertEquals(PRODUCT.getProductCategory(), convertedProduct.getProductCategory());
        assertEquals(PRODUCT, convertedProduct);
    }
}
