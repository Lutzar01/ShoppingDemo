package com.lutzarDemos.shoppingdemo.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for USER entity
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/10
 */
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
