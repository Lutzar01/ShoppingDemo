package com.lutzarDemos.shoppingdemo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Data Transfer Object for CART entity
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/10
 */
@Data
public class CartDto {
    private Long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
