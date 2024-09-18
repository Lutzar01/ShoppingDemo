package com.lutzarDemos.shoppingdemo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Handles the log in requirements from a USER
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/17
 */
@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
