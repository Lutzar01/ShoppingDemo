package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Cart;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// Handles HTTP requests and returns a response for carts
// otherwise returns HTTP status error
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    // Handles HTTP request to get a Cart by ID
    // Returns response "Success" and the Cart with params
    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success!", cart));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to clear an existing Cart
    // returns the response "Clear Cart Success!" and the cart ID that was cleared
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", cartId));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to get the total amount of an existing Cart
    // returns the response "Total Price!" and the total price of the Cart
    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price!", totalPrice));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
