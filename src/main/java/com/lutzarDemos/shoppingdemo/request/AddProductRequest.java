package com.lutzarDemos.shoppingdemo.request;

import com.lutzarDemos.shoppingdemo.model.Category;
import com.lutzarDemos.shoppingdemo.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

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
