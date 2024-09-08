package com.my.appy.demo.service;

import com.my.appy.demo.exception.ExceptionHandler; // Import ExceptionHandler here
import com.my.appy.demo.model.User;
import com.my.appy.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        validateUser(user);
        Long insertedId = userRepository.insertUser(user);
        user.setId(insertedId);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public void updateUser(Long id, User user) {
        validateUser(user);
        if (!userRepository.existsUserById(id)) {
            throw new ExceptionHandler("User not found with id " + id);
        }
        userRepository.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsUserById(id)) {
            throw new ExceptionHandler("User not found with id " + id);
        }
        userRepository.deleteUser(id);
    }

    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
