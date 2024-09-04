package com.lutzarDemos.shoppingdemo.dto;

import com.lutzarDemos.shoppingdemo.model.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

// Data transfer object for a product
// *NOTE* REQUIRED FOR FUTURE EXPANSION, CURRENTLY NOT IN USE
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
