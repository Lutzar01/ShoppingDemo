package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.dto.ProductDto;
import com.lutzarDemos.shoppingdemo.exceptions.AlreadyExistsException;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Product;
import com.lutzarDemos.shoppingdemo.request.AddProductRequest;
import com.lutzarDemos.shoppingdemo.request.ProductUpdateRequest;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Handles HTTP requests and returns a response for PRODUCTs
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/11
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    // Handles HTTP request to get all products
    // returns a list of product data transfer objects with params
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
    }

    // Handles HTTP request to get a product by its ID
    // returns the requested product data transfer object with params
    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("success", productDto));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Handles HTTP request to add a new PRODUCT
     *
     * @param product   The request body containing the params of the PRODUCT being added
     * @return          If success, ok response with the new PRODUCT
     *                  If failure, CONFLICT response with message
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success!", theProduct));

        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to update an existing product
    // returns the updated product with params
    @PutMapping("/product/{productId}/update")
    public  ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product theProduct = productService.updatedProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update product success!", theProduct));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to delete an existing product
    // returns the product ID that was deleted
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to find all products by requested brand and name
    // returns a list of product data transfer objects that match the request with params
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to find all products by requested brand and category
    // returns a list of product data transfer objects that match the request with params
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    // Handles HTTP request to find all products by requested name
    // returns a list of product data transfer objects that match the request with params
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    // Handles HTTP request to find all products by requested brand
    // returns a list of product data transfer objects that match the request with params
    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to find all products by requested category
    // returns a list of product data transfer objects that match the request with params
    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }

            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));

        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    // Handles HTTP request to count all products by requested brand and name
    // returns a count as LONG that references the number of products that match the request
    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

}
