package com.lutzarDemos.shoppingdemo.request;

import lombok.Data;

/**
 * Handles the data of the user that is needed to create a new USER
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/09
 */
@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
