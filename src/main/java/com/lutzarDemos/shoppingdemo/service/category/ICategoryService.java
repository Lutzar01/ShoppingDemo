package com.lutzarDemos.shoppingdemo.service.category;

import com.lutzarDemos.shoppingdemo.model.Category;

import java.util.List;

/**
 * Contains base methods relating to the CATEGORY entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/30
 */
public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
