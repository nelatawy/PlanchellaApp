package com.planchella.utils;

import com.planchella.Services.JWTService;
import com.planchella.entities.AuthUserEntity;
import com.planchella.repositories.users.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Helper class to extract authenticated user IDs from JWT tokens.
 * This centralizes authentication logic used across all route controllers.
 */
@Component
public class UserAuthenticationHelper {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthUserRepository authUserRepository;

    /**
     * Extracts the user ID from the Authorization header.
     *
     * @param authHeader The Authorization header containing "Bearer {token}"
     * @return The user ID of the authenticated user
     * @throws RuntimeException if the token is invalid or user is not found
     */
    public Long extractUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }

        // Extract JWT token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Extract the identifier from the token (Google ID or username)
        String identifier = jwtService.extractUsername(token);

        // Find the auth user by provider ID (for Google auth) or username (for local
        // auth)
        AuthUserEntity authUser = authUserRepository.findByProviderId(identifier);
        if (authUser == null) {
            // Try by username if not found by provider ID (for local authentication)
            authUser = authUserRepository.findByUsername(identifier);
            if (authUser == null) {
                throw new RuntimeException("User not found for the provided token");
            }
        }

        return authUser.getUserId();
    }
}
