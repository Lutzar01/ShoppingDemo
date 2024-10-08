package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.dto.ImageDto;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Image;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//
/**
 * Handles HTTP requests and returns a response for IMAGEs
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/30
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    // Handles HTTP request to upload and save one or more images to a product
    // returns a list of images that reflect the request with params
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity
                    .ok(new ApiResponse("Upload success!", imageDtos));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload Failed!", e.getMessage()));
        }
    }

    /**
     * Handles HTTP request to download an existing IMAGE of a PRODUCT
     *     returns the requested image
     *
     * @param imageId           Identity of the IMAGE
     * @return                  If success, ok response with IMAGE resource
     *                          If failure, NOT_FOUND status
     * @throws SQLException     SQL Exception with message
     */
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        try {
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND).body(null);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    // Handles HTTP requests to update an existing image of a product
    // returns the updated image with params
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity
                        .ok(new ApiResponse("Update Success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update Failed!", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    // Handles HTTP request to delete an existing image of a product
    // returns a null value confirming the request was successful
    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity
                        .ok(new ApiResponse("Delete Success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Delete failed!", null));
    }
}
