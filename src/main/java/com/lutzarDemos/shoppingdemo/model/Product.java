package com.lutzarDemos.shoppingdemo.model;

import java.math.BigDecimal;
import java.util.List;

public class Product {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    // Used to track the number of products in stock
    private int inventory;
    private String description;

    private Category category;

    private List<Image> imageList;
}
