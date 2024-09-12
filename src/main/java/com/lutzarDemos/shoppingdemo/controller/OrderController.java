package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.dto.OrderDto;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Order;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Handles HTTP requests and returns a response for an ORDER
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/10
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    /**
     * Handles HTTP request to create a new ORDER DTO in the ORDER REPOSITORY
     *
     * @param userId    The ID of the USER creating the request
     * @return          If success, ok response with ORDER DTO
     *                  If failure, INTERNAL_SERVER_ERROR response with message
     */
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity
                    .ok(new ApiResponse("New Order Success!", orderDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An Error Occurred!", e.getMessage()));
        }
    }

    /**
     * Handles HTTP request to get an ORDER DTO by its ID
     *
     * @param orderId   The ID of the ORDER being requested
     * @return          If success, ok response with ORDER DTO
     *                  If failure, NOT_FOUND response with message
     */
    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity
                    .ok(new ApiResponse("Order: " + orderId + " was found!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("Order Not Found!", e.getMessage()));
        }
    }

    /**
     * Handles HTTP request to get a list of ORDER DTOs by a USER ID
     *
     * @param userId    The ID of the USER who owns the ORDERs
     * @return          If success, ok response with list of ORDER DTOs
     *                  If failure, NOT_FOUND response with message
     */
    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getOrdersByUser(@PathVariable Long userId) {
        try {
            List<OrderDto> orderList = orderService.getUserOrders(userId);
            return ResponseEntity
                    .ok(new ApiResponse("Orders Found!", orderList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("No Orders Found!", e.getMessage()));
        }
    }


}
