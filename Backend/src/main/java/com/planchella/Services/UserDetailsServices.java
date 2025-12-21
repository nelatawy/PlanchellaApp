package com.planchella.Services;

import com.planchella.entities.AuthUserEntity;
import com.planchella.domain.UserPrincipal;
import com.planchella.repositories.users.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private AuthUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // The "username" here is actually the userId string from the JWT sub claim
        try {
            Long userId = Long.parseLong(username);
            AuthUserEntity user = userRepo.findByUserId(userId);
            if (user != null) {
                return new UserPrincipal(user);
            }
        } catch (NumberFormatException e) {
            // Fallback for local logins where actual username might be used during initial
            // authentication
            AuthUserEntity userByUsername = userRepo.findByUsername(username);
            if (userByUsername != null) {
                return new UserPrincipal(userByUsername);
            }
        }

        throw new UsernameNotFoundException(username + " not found");
    }
}