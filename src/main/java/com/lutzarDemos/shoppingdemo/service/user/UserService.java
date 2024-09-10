package com.lutzarDemos.shoppingdemo.service.user;

import com.lutzarDemos.shoppingdemo.exceptions.AlreadyExistsException;
import com.lutzarDemos.shoppingdemo.exceptions.ResourceNotFoundException;
import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.repository.UserRepository;
import com.lutzarDemos.shoppingdemo.request.CreateUserRequest;
import com.lutzarDemos.shoppingdemo.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Contains override methods relating to the USER entity
 *      for business logic and application functionality
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/09
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    /**
     * Finds an existing USER in the USER REPOSITORY
     *
     * @param userId    The ID of the USER
     * @return          The USER if they exist
     */
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Creates a new USER in the USER REPOSITORY
     *
     * @param request   Contains the values required to create a new USER
     * @return          The new USER and saves it to the USER REPOSITORY
     */
    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException("Uh-oh! " + request.getEmail() + " already exists!"));
    }

    /**
     * Updates an existing USER's data in the USER REPOSITORY
     *
     * @param request   Contains the values allowed to be updated for an existing USER
     * @param userId    The ID of the USER being updated
     * @return          The updated USER and saves it to the USER REPOSITORY
     */
    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Deletes an existing USER from the USER REPOSITORY
     *
     * @param userId    The ID of the USER being deleted
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository :: delete, () -> {
                    throw new ResourceNotFoundException("User not found!");
                });
    }
}
