package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Manages USER entity and saves data to the MySQL DB
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/11
 */
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
