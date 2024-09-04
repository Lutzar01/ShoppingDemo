package com.lutzarDemos.shoppingdemo.service.category;

import com.lutzarDemos.shoppingdemo.exceptions.AlreadyExistsException;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.Category;
import com.lutzarDemos.shoppingdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Contains override methods  relating to the category entity for business logic and application functionality
@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    // Takes in category ID and searches for it in the DB
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category Not Found!"));
    }

    // Takes in category name and searches for it in the DB
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    // Finds all categories that existing in the DB
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Takes in a new category with params and saves it to the DB
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists."));
    }

    // Takes in new params for an existing category and an existing category ID
    // updates the category in the DB
    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }) .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

    // Takes in an existing category ID and removes it from the DB
    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category Not Found!");
                });
    }
}
