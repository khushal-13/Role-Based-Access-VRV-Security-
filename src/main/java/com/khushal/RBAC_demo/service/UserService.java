package com.khushal.RBAC_demo.service;

import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Fetch all users from the repository
    public List<User> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    // Save the user details
    public void saveUser(User user) {
        log.info("Saving user: {}", user.getUserName());
        userRepository.save(user);
    }

    // Create and save a new user with encrypted password and default roles
    public User saveNewUser(User user) {
        log.info("Creating new user: {}", user.getUserName());
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set default role as USER
        user.setRoles(List.of("USER"));
        // Set user as active
        user.setActive(true);
        return userRepository.save(user);
    }

    // Find a user by their username
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    // Delete the currently authenticated user from the repository
    public boolean deleteUser() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username != null) {
            log.info("Deleting user: {}", username);
            userRepository.deleteByUserName(username);
            return true;
        }

        log.warn("No authenticated user for deletion");
        return false;
    }

    // Update the current user's information
    public User updateUser(User user) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find the user to update
        User oldUser = userRepository.findByUserName(username);
        if (oldUser != null) {
            // Update user details
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            return saveNewUser(oldUser);
        }
        return null;
    }

    // Create and save a new admin user
    public User saveNewAdmin(User user) {
        log.info("Creating new admin user: {}", user.getUserName());
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set roles as both USER and ADMIN
        user.setRoles(List.of("USER", "ADMIN"));
        // Set user as active
        user.setActive(true);
        return userRepository.save(user);
    }
}
