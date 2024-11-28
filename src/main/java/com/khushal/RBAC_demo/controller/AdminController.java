package com.khushal.RBAC_demo.controller;

import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService; // Handles user-related operations

    // Fetch all users
    @GetMapping("/get-users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    // Create a new admin user
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveNewAdmin(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
