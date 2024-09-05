package com.lutzarDemos.shoppingdemo.service.cart;

import com.lutzarDemos.shoppingdemo.model.CartItem;

// Contains base methods relating to the cart item entity for business logic and application functionality
public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity, CartItem cartItem);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
