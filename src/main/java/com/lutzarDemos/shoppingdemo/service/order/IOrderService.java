package com.lutzarDemos.shoppingdemo.service.order;

import com.lutzarDemos.shoppingdemo.model.Order;

import java.util.List;

/**
 * Contains base methods relating to the ORDER entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/07
 */
public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getUserOrders(Long userId);
}
