package com.lutzarDemos.shoppingdemo.controller;

import com.lutzarDemos.shoppingdemo.dto.UserDto;
import com.lutzarDemos.shoppingdemo.exceptions.AlreadyExistsException;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.request.CreateUserRequest;
import com.lutzarDemos.shoppingdemo.request.UpdateUserRequest;
import com.lutzarDemos.shoppingdemo.response.ApiResponse;
import com.lutzarDemos.shoppingdemo.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

/**
 * Handles HTTP requests and returns a response for a USER
 *      otherwise returns HTTP status error
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/10
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    /**
     * Handles HTTP request to get a USER by their ID
     *      and converts USER to a USER DTO
     *
     * @param userId    The ID of the USER being requested
     * @return          If success, ok response with USER DTO
     *                  If failure, NOT_FOUND response with message
     */
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity
                    .ok(new ApiResponse("User Found!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Handles HTTP request to create a new USER
     *      and converts USER to a USER DTO
     *
     * @param request   Contains the new USER request params
     * @return          If success, ok response with new USER DTO
     *                  If failure, CONFLICT response with message
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity
                    .ok(new ApiResponse("Created New User!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Handles HTTP request to update an existing USER
     *      and converts USER to USER DTO
     *
     * @param request   Contains the new params of the USER being updated
     * @param userId    The ID of the USER being updated
     * @return          If success, ok response with updated USER DTO
     *                  If failure, NOT_FOUND response with message
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity
                    .ok(new ApiResponse("User " + userId + " Updated!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    /**
     * Handles HTTP request to delete an existing USER
     *
     * @param userId    The ID of the USER being deleted
     * @return          If success, ok response with message referencing USER deleted
     *                  If failure, NOT_FOUND response with message
     */
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity
                    .ok(new ApiResponse("User " + userId + " deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
