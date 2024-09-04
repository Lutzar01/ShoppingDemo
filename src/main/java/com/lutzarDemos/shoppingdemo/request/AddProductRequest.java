package com.lutzarDemos.shoppingdemo.request;

import com.lutzarDemos.shoppingdemo.model.Category;
import lombok.Data;

import java.math.BigDecimal;

// Handles adding new data objects for products
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
