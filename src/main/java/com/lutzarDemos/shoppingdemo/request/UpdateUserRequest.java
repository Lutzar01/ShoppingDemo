package com.lutzarDemos.shoppingdemo.request;

import lombok.Data;

/**
 * Handles the data of the USER that is allowed to be updated
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/09
 */
@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
}
