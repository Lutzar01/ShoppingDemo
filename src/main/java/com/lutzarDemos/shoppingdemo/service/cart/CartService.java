package com.lutzarDemos.shoppingdemo.service.cart;

import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Cart;
import com.lutzarDemos.shoppingdemo.repository.CartItemRepository;
import com.lutzarDemos.shoppingdemo.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// Contains override methods relating to the cart entity for business logic and application functionality
@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // Takes in cart ID, updates total amount, saves cart
    // throws ResourceNotFoundException if ID does not exist
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    // Takes in cart ID
    // deletes all cart items in repository
    // clears cart items
    // deletes cart
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    // Takes in cart ID
    // returns total amount of cart
    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
