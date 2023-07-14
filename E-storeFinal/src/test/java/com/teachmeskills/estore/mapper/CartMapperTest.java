package com.teachmeskills.estore.mapper;

import com.teachmeskills.estore.domain.Cart;
import com.teachmeskills.estore.dto.CartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.teachmeskills.estore.test_utils.Constants.MapperConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    private final Long cartId = 1L;
    private final Integer cartCount = 2;
    private final Cart cart = Cart.builder()
            .id(cartId)
            .user(USER)
            .product(PRODUCT)
            .count(cartCount)
            .cart(true)
            .favorite(false)
            .build();
    private final CartDto cartDto = CartDto.builder()
            .id(cartId)
            .userDto(USER_DTO)
            .productDto(PRODUCT_DTO)
            .count(cartCount)
            .cart(true)
            .favorite(false)
            .build();
    private final List<Cart> carts = List.of(cart, cart);
    private final List<CartDto> cartDtos = List.of(cartDto, cartDto);

    @Test
    void test_convertToCartDto() {
        CartDto convertedCart = cartMapper.convertToCartDto(cart);

        assertEquals(cartDto, convertedCart);
    }

    @Test
    void test_convertToCartDtos() {
        List<CartDto> convertedCartDtos = cartMapper.convertToCartDtos(carts);

        assertEquals(cartDtos, convertedCartDtos);
    }
}
