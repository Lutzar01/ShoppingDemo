package com.lutzarDemos.shoppingdemo.service.cart;

import com.lutzarDemos.shoppingdemo.model.Cart;

import java.math.BigDecimal;

// Contains base methods relating to the cart entity for business logic and application functionality
public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
