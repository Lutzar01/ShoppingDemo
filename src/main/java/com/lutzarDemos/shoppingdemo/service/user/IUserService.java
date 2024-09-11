package com.lutzarDemos.shoppingdemo.service.user;

import com.lutzarDemos.shoppingdemo.dto.UserDto;
import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.request.CreateUserRequest;
import com.lutzarDemos.shoppingdemo.request.UpdateUserRequest;

/**
 * Contains base methods relating to the USER entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.2, 2024/09/10
 */
public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
}
