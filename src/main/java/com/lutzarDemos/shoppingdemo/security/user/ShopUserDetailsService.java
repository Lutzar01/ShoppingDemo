package com.lutzarDemos.shoppingdemo.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implements the UserDetailsService spring security framework for USERs
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/11
 */
public class ShopUserDetailsService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}