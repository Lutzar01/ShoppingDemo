package com.lutzarDemos.shoppingdemo.service.cart;

import com.lutzarDemos.shoppingdemo.model.Cart;

import java.math.BigDecimal;

/**
 * Contains base methods relating to the CART entity
 * for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/07
 */
public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
