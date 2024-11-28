package com.khushal.RBAC_demo.controller;

import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService; // Handles user-related operations

    // Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getByUserName(@PathVariable String username) {
        try {
            return new ResponseEntity<>(userService.findByUserName(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return NOT_FOUND if user is not found
        }
    }

    // Update user information
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(user); // Update user details
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return NOT_FOUND if update fails
        }
    }

    // Delete the current user
    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {
        if (userService.deleteUser()) {
            return new ResponseEntity<>(HttpStatus.OK); // Return OK if deletion is successful
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return NOT_FOUND if deletion fails
    }
}
