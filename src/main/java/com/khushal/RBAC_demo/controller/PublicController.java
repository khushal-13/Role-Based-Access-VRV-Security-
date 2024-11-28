package com.khushal.RBAC_demo.controller;

import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.service.UserDetailServiceImpl;
import com.khushal.RBAC_demo.service.UserService;
import com.khushal.RBAC_demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService; // Handles user-related operations

    @Autowired
    private AuthenticationManager authenticationManager; // Authenticates user credentials

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl; // Fetches user details for authentication

    @Autowired
    private JwtUtil jwtUtil; // Utility class for JWT operations

    // User signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveNewUser(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error creating User: {}", user.getUserName());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            // Generate JWT token upon successful authentication
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUserName());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in login: {}", user.getUserName());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
