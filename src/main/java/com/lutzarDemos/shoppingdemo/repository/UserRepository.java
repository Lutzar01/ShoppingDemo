package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manages USER entity and saves data to the MySQL DB
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/09
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
