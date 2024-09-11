package com.lutzarDemos.shoppingdemo.service.order;

import com.lutzarDemos.shoppingdemo.dto.OrderDto;
import com.lutzarDemos.shoppingdemo.model.Order;

import java.util.List;

/**
 * Contains base methods relating to the ORDER entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/10
 */
public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
