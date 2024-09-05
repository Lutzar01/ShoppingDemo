package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

// Manages cart item entity and saves data to the MySQL DB
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
