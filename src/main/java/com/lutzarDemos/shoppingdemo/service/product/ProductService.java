package com.lutzarDemos.shoppingdemo.service.product;

import com.lutzarDemos.shoppingdemo.dto.ImageDto;
import com.lutzarDemos.shoppingdemo.dto.ProductDto;
import com.lutzarDemos.shoppingdemo.exceptions.AlreadyExistsException;
import com.lutzarDemos.shoppingdemo.exceptions.ProductNotFoundException;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Category;
import com.lutzarDemos.shoppingdemo.model.Image;
import com.lutzarDemos.shoppingdemo.model.Product;
import com.lutzarDemos.shoppingdemo.repository.CategoryRepository;
import com.lutzarDemos.shoppingdemo.repository.ImageRepository;
import com.lutzarDemos.shoppingdemo.repository.ProductRepository;
import com.lutzarDemos.shoppingdemo.request.AddProductRequest;
import com.lutzarDemos.shoppingdemo.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Contains override methods relating to the PRODUCT entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.3, 2024/09/11
 */
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    /**
     * Saves a new PRODUCT to the DB
     *      1) Check to see if the PRODUCT already exists
     *          If true, throws AlreadyExistsException
     *      2) Check to see if the CATEGORY already exists
     *          If yes, set it as the new PRODUCT CATEGORY
     *          If no, create new CATEGORY and save
     *
     * @param request   The class containing the params of the PRODUCT being added
     * @return          Saved PRODUCT
     */
    @Override
    public Product addProduct(AddProductRequest request) {
        if (productExists(request.getName(), request.getBrand())) {
            throw new AlreadyExistsException
                    (request.getBrand() + " " + request.getName() + " Already Exists! Try Updating The PRODUCT instead!");
        }

        Category category = Optional
                .ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    /**
     * Helper method to see if the PRODUCT already exists in the DB
     *
     * @param name      Name of the PRODUCT
     * @param brand     Brand of the PRODUCT
     */
    private boolean productExists(String name, String brand) {
        return productRepository.existByNameAndBrand(name, brand);
    }

    /**
     * Helper method to create a new PRODUCT
     *
     * @param request   The class containing the params of the PRODUCT being created
     * @param category  The CATEGORY of the PRODUCT being created
     * @return          New PRODUCT with params
     */
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    // Takes in a product ID and searches for it in the DB
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));
    }

    // Takes in a product ID and removes it from the DB
    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ResourceNotFoundException("Product not found!");});
    }

    // Takes in a ProductUpdateRequest with params and an existing product ID in the DB
    // replaces existing product with new params
    @Override
    public Product updatedProduct(ProductUpdateRequest request, Long productId) {
        return productRepository
                .findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()->new ResourceNotFoundException("Product not found!"));

    }

    /**
     * Helper method to update an existing PRODUCT
     *      any update overrides existing PRODUCT param
     *
     * @param existingProduct   PRODUCT being updated in the DB
     * @param request           The class containing the PRODUCT params being updated
     * @return                  Updated PRODUCT with params
     */
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    // Finds all products in the DB
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Takes in a category name linked to existing products
    // searches for all products with the category name as a param in the DB
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    // Takes in a brand name linked to existing products
    // searches for all products with the brand name as a param in the DB
    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // Takes in a category name and brand name linked to existing products
    // searches for all products with the category and brand name as params in the DB
    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    // Takes in a product name linked to existing products
    // searches for all products with the product name as a param in the DB
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    // Takes in a brand name and product name linked to existing products
    // searches for all products with the brand name and product name as params in the DB
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    // Takes in a brand name and product name linked to existing products
    // counts for all products with the brand name and product name as params in the DB
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    // Takes in a list of products
    // returns a list of products that have been converted to product dtos
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    // Takes in an existing product entity
    // maps a the product to a product dto
    // creates a list of the images linked to the product
    // maps and creates a list of image dtos from image list
    // updates product dto with list of image dtos
    // returns converted product dto with image dtos
    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
