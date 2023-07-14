package com.teachmeskills.estore.service.impl;

import com.teachmeskills.estore.domain.Cart;
import com.teachmeskills.estore.domain.Product;
import com.teachmeskills.estore.domain.User;
import com.teachmeskills.estore.dto.CartDto;
import com.teachmeskills.estore.dto.ProductDto;
import com.teachmeskills.estore.mapper.CartMapper;
import com.teachmeskills.estore.model.Location;
import com.teachmeskills.estore.repository.CartRepository;
import com.teachmeskills.estore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public List<CartDto> getSelectedProducts(Long userId, Location location) {
        if (location.isCart()) {
            return cartMapper.convertToCartDtos(cartRepository.getCartProducts(userId));
        } else {
            return cartMapper.convertToCartDtos(cartRepository.getFavoriteProducts(userId));
        }
    }

    @Transactional
    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
        cartRepository.deleteCartByUserIdAndCart(userId, true);
    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
        List<ProductDto> productDtos = new ArrayList<>();
        List<CartDto> carts = cartMapper.convertToCartDtos(cartRepository.getCartProducts(userId));
        for (CartDto cart : carts) {
            Integer count = cart.getCount();
            while (count > 0) {
                productDtos.add(cart.getProductDto());
                count--;
            }
        }
        return productDtos;
    }

    @Override
    public List<Map<Long, Long>> getMostFavorite() {
        return cartRepository.getMostFavorite();
    }

    @Transactional
    @Override
    public void addSelectedProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            if (userFavorite.isEmpty()) {
                addProduct(userId, productId, location);
            }
        } else {
            Optional<Cart> userCart = cartRepository.getUserCart(userId, productId);
            if (userCart.isEmpty()) {
                addProduct(userId, productId, location);
            } else {
                Cart cart = userCart.get();
                Integer count = cart.getCount();
                cart.setCount(++count);
            }
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long userId, Long productId, Location location) {
        if (location.isFavorite()) {
            Optional<Cart> userFavorite = cartRepository.getUserFavorite(userId, productId);
            userFavorite.ifPresent(cartRepository::delete);
        } else {
            Optional<Cart> userCart = cartRepository.getUserCart(userId, productId);
            if (userCart.isPresent()) {
                Cart cart = userCart.get();
                Integer count = cart.getCount();
                if (count > 1) {
                    cart.setCount(--count);
                } else {
                    cartRepository.delete(cart);
                }
            }
        }
    }

    private void addProduct(Long userId, Long productId, Location location) {
        Cart cart = getCart(userId, productId, location);
        cartRepository.save(cart);
    }

    private Cart getCart(Long userId, Long productId, Location location) {
        return Cart.builder()
                .user(User.builder()
                        .id(userId)
                        .build())
                .product(Product.builder()
                        .id(productId)
                        .build())
                .favorite(location.isFavorite())
                .cart(location.isCart())
                .count(1)
                .build();
    }
}
