package com.lutzarDemos.shoppingdemo.security.user;

import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implements the UserDetailsService spring security framework for USERs
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/11
 */
@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    /**
     * Finds USER by email and builds user details from USER
     *
     * @param email     The email of the USER
     * @return          If success, the user details of the USER
     * @throws UsernameNotFoundException    If failure
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional
                .ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}