package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manages ORDER entity and saves data to the MySQL DB
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/07
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
}
