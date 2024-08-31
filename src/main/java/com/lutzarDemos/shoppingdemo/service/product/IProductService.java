package com.lutzarDemos.shoppingdemo.service.product;

import com.lutzarDemos.shoppingdemo.model.Product;
import com.lutzarDemos.shoppingdemo.request.AddProductRequest;
import com.lutzarDemos.shoppingdemo.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);

    void deleteProductById(Long id);
    Product updatedProductById(ProductUpdateRequest product, Long productId);

    // List all products
    List<Product> getAllProducts();
    // Find by the name of the category
    List<Product> getProductsByCategory(String category);
    // Find by the name of the brand
    List<Product> getProductsByBrand(String brand);
    // Find by category and brand
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    // Find by name
    List<Product> getProductByName(String name);
    // Find by brand and name
    List<Product> getProductByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
