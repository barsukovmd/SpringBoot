package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Order;
import com.teachmeskills.estore.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class, uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "products", target = "productDtos")
    OrderDto convertToOrderDto(Order order);
}
