package com.khushal.RBAC_demo.filter;

import com.khushal.RBAC_demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService; // Loads user details for authentication

    @Autowired
    private JwtUtil jwtUtil; // Utility class for JWT operations

    // Filters requests to validate JWT and authenticate users
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Extract JWT and username from the Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Remove "Bearer " prefix
            username = jwtUtil.extractUsername(jwt); // Extract username from JWT
        }

        // Authenticate user if JWT is valid
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Load user details
            if (jwtUtil.validateToken(jwt)) { // Validate the JWT
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set details from the request
                SecurityContextHolder.getContext().setAuthentication(auth); // Set authentication context
            }
        }

        // Proceed with the filter chain
        chain.doFilter(request, response);
    }
}
