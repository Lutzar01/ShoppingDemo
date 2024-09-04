package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// Manages category entity and saves data to the MySQL DB
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
