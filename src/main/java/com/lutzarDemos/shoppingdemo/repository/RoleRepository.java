package com.lutzarDemos.shoppingdemo.repository;

import com.lutzarDemos.shoppingdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

/**
 * Manages ROLE entity and saves data to the MySQL DB
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/17
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
