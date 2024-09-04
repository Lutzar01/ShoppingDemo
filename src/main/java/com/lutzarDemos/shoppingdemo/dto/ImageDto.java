package com.lutzarDemos.shoppingdemo.dto;

import lombok.Data;

// Data Transfer object for an image
@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
