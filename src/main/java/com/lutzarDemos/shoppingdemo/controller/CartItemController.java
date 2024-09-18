package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Cart;
import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.cart.ICartItemService;
import com.lutzarDemos.shoppingdemo.service.cart.ICartService;
import com.lutzarDemos.shoppingdemo.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Handles HTTP requests and returns a response for a CART
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.4, 2024/09/17
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    /**
     * Handles HTTP request to add a new CART ITEM
     *
     * @param productId     The ID of the PRODUCT being added to the CART
     * @param quantity      The amount of the PRODUCT being added to the CART
     * @return              If success, ok response with message
     *                      If failure to add item, NOT_FOUND response with message
     *                      If failure to get authenticated USER, UNAUTHORIZED response with message
     */
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = user.getCart();
            if (cart == null){
                cart = cartService.initializeNewCart(user);
            } else {
                cart = cartService.getCartByUserId(user.getId());
            }
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success!", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to remove an existing Product from an existing Cart
    // returns the response "Item Removed!"
    @DeleteMapping("/cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Item Removed!", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to update an existing Product quantity in an existing Cart
    // returns the response "Update Item Success!"
    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success!", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
