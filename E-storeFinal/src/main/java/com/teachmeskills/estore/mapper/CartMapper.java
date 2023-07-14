package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Cart;
import com.teachmeskills.estore.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface CartMapper {

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "product", target = "productDto")
    @Mapping(source = "product.productCategory.category", target = "productDto.category")
    CartDto convertToCartDto(Cart cart);

    List<CartDto> convertToCartDtos(List<Cart> carts);
}
