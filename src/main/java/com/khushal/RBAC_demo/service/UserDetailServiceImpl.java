package com.khushal.RBAC_demo.service;

import com.khushal.RBAC_demo.entity.User;
import com.khushal.RBAC_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;  // Repository to fetch user details from the database

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database using the repository
        User user = userRepository.findByUserName(username);

        // If the user exists, return a Spring Security User object with the username, password, and roles
        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUserName()) // Set username
                    .password(user.getPassword()) // Set password
                    .roles(user.getRoles().toArray(new String[0])) // Convert roles to a string array and set
                    .build();
        }

        // If the user is not found, throw a UsernameNotFoundException
        throw new UsernameNotFoundException("User not found with username : " + username);
    }
}
