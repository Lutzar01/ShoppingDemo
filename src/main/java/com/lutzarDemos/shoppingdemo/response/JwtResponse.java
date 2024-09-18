package com.lutzarDemos.shoppingdemo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Handles Jwt Response for authentication
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
}
