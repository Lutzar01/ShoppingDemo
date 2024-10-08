package com.lutzarDemos.shoppingdemo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// Handles HTTP responses
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;
}
