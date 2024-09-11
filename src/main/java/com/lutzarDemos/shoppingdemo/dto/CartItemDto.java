package com.lutzarDemos.shoppingdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for CART ITEM entity
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/10
 */
@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
