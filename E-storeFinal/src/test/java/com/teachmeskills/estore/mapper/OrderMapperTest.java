package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Order;
import com.teachmeskills.estore.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.USER;
import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.USER_DTO;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void test_convertToOrderDto() {
        Long orderId = 1L;
        String orderName = "name";
        LocalDate date = LocalDate.of(2002, 2, 2);
        Order order = Order.builder()
                .id(orderId)
                .name(orderName)
                .date(date)
                .user(USER)
                .build();
        OrderDto orderDto = OrderDto.builder()
                .id(orderId)
                .name(orderName)
                .date(date)
                .userDto(USER_DTO)
                .build();

        OrderDto convertedOrder = orderMapper.convertToOrderDto(order);

        assertEquals(orderDto, convertedOrder);
    }
}
