package com.lutzarDemos.shoppingdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for ORDER ITEM entity
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/10
 */
@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
