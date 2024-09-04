package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Manages image entity and saves data to the MySQL DB
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);

}
