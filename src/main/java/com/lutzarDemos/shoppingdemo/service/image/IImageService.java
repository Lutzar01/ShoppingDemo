package com.lutzarDemos.shoppingdemo.service.image;

import com.lutzarDemos.shoppingdemo.dto.ImageDto;
import com.lutzarDemos.shoppingdemo.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Contains base methods relating to the image entity for business logic and application functionality
public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
