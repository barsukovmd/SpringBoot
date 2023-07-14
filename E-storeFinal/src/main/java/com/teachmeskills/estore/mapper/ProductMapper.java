package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Product;
import com.teachmeskills.estore.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface ProductMapper {

    @Mapping(source = "product.productCategory.category", target = "category")
    ProductDto convertToProductDto(Product product);

    @InheritInverseConfiguration
    @Mapping(target = "productCategory", expression = "java(new ProductCategory(dto.getCategory()))")
    Product convertToProduct(ProductDto productDto);

    List<ProductDto> convertToProductDtos(List<Product> products);
}
