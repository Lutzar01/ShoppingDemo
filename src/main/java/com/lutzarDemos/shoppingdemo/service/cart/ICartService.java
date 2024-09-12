package com.lutzarDemos.shoppingdemo.service.cart;

import com.lutzarDemos.shoppingdemo.model.Cart;
import com.lutzarDemos.shoppingdemo.model.User;

import java.math.BigDecimal;

/**
 * Contains base methods relating to the CART entity
 * for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/10
 */
public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
}
