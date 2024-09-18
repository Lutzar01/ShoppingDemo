package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.request.LoginRequest;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.response.JwtResponse;
import com.lutzarDemos.shoppingdemo.security.jwt.JwtUtils;
import com.lutzarDemos.shoppingdemo.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles HTTP requests and returns a response for AUTHENTICATION
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/17
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * Handles HTTP request to log in a USER
     *
     * @param request   username & password
     * @return          If success, user details & jwt with message
     *                  If failure, UNAUTHORIZED response with message
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtReponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity
                    .ok(new ApiResponse("Login Successful!", jwtReponse));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
