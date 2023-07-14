package com.teachmeskills.estore.service;

import com.teachmeskills.estore.domain.Product;
import com.teachmeskills.estore.dto.OrderDto;
import com.teachmeskills.estore.dto.ProductDto;

import java.util.List;

public interface OrderService {

    Long createOrder(Long id);

    void saveProductInOrderConfigurations(Long id, List<Product> products);

    List<OrderDto> getOrdersById(Long id);

    boolean checkOrderNumber(String number);

    void saveUserOrder(Long userId, List<ProductDto> productsDto);
}
