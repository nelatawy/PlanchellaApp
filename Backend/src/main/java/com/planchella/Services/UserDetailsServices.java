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
        AuthUserEntity users = userRepo.findByUsername(username);

        if (users == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException(username+" not found");
        }

        return new UserPrincipal(users);
    }
}