package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

// Manages cart entity and saves data to the MySQL DB
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
