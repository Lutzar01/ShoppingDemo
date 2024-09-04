package com.lutzarDemos.shoppingdemo.exceptions;

// *NOTE* PENDING REMOVAL, EXCEPTION NOT NECESSARY
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
