package com.planchella.routes;

import com.planchella.DTOs.EventDTO;
import com.planchella.Services.*;
import com.planchella.domain.Event;
import com.planchella.domain.Membership;
import org.springframework.web.bind.annotation.*;

import com.planchella.DTOs.UserDTO;
import com.planchella.domain.User;
import com.planchella.mappers.UserMapper;
import com.planchella.repositories.users.AuthUserRepository;
import com.planchella.entities.AuthUserEntity;
import com.planchella.utils.UserAuthenticationHelper;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserRoutes {

    @Autowired
    UserService userService;

    @Autowired
    MembershipService membershipService;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    UserAuthenticationHelper authHelper;
    @Autowired
    private UserSpecificEventService userSpecificEventService;

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

    @GetMapping("/{user_id}/events")
    public List<EventDTO> getUserEvents(@PathVariable Long user_id, @RequestParam int count, @RequestParam int offset,
                                        @RequestHeader("Authorization") String authHeader) {
        Long requestingUserId = authHelper.extractUserId(authHeader);
        List<Event> events = userService.getUserEvents(user_id, count, offset);
        return userSpecificEventService.enrichEventsForUser(events, requestingUserId);
    }

    @GetMapping("/{user_id}/starred")
    public List<EventDTO> getStarredEvents(@PathVariable Long user_id, @RequestParam int count,
            @RequestParam int offset,
            @RequestHeader("Authorization") String authHeader) {
        Long requestingUserId = authHelper.extractUserId(authHeader);
        List<Event> events = userService.getStarredEvents(user_id, count, offset);
        return userSpecificEventService.enrichEventsForUser(events, requestingUserId);
    }

    @GetMapping("/{user_id}/memberships")
    public List<Membership> getUserMemberships(@PathVariable Long user_id, @RequestParam int count,
            @RequestParam int offset) {
        User user = userService.getUser(user_id);
        return membershipService.getMembershipsByUser(user);
    }

    @PatchMapping
    public void updateUser(@RequestBody UserDTO data,
            @RequestHeader("Authorization") String authHeader) {
        Long requestingUserId = authHelper.extractUserId(authHeader);
        User newUserData = UserMapper.DTOtoDomain(data);
        this.userService.updateUser(requestingUserId, newUserData);
    }

    @PutMapping
    public void addUser(@RequestBody UserDTO data) {
        User user = UserMapper.DTOtoDomain(data);
        this.userService.addUser(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestHeader("Authorization") String authHeader) {
        Long requestingUserId = authHelper.extractUserId(authHeader);
        this.userService.deleteUser(requestingUserId);
    }

}
