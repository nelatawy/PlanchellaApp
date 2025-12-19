package com.planchella.routes;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import com.planchella.DTOs.UserDTO;
import com.planchella.Services.UserService;
import com.planchella.Services.JWTService;
import com.planchella.domain.User;
import com.planchella.mappers.UserMapper;
import com.planchella.repositories.users.AuthUserRepository;
import com.planchella.entities.AuthUserEntity;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRoutes {

    @Autowired
    UserService userService;
    
    @Autowired
    JWTService jwtService;
    
    @Autowired
    AuthUserRepository authUserRepository;

    @GetMapping("/me")
    public UserDTO getCurrentUser(@RequestHeader("Authorization") String authHeader) {

        // Get JWT token
        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Get the google id
        String googleId = jwtService.extractUsername(token); // This gets the 'sub' (Google ID)

        // Load the authentication data of the user using the google id
        AuthUserEntity authUser = authUserRepository.findByProviderId(googleId);
        if (authUser == null) {
            // Try by username if not found by provider ID
            authUser = authUserRepository.findByUsername(googleId);
            if (authUser == null) {
                throw new RuntimeException("User not found");
            }
        }
        
        User user = this.userService.getUser(authUser.getUserId());
        return UserMapper.domainToDTO(user);
    }

    @GetMapping("/{user_id}")
    public UserDTO getUser(@PathVariable Long user_id) {
        User user = this.userService.getUser(user_id);
        return UserMapper.domainToDTO(user);
    }

    @PatchMapping("/{user_id}")
    public void updateUser(@PathVariable Long user_id, @RequestBody UserDTO data) {
        User newUserData = UserMapper.DTOtoDomain(data);
        this.userService.updateUser(user_id, newUserData);
    }

    @PutMapping
    public void addUser(@RequestBody UserDTO data) {
        User user = UserMapper.DTOtoDomain(data);
        this.userService.addUser(user);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable Long user_id) {
        this.userService.deleteUser(user_id);
    }

}
