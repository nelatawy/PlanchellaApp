package com.planchella.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.planchella.domain.AuthUser;
import com.planchella.domain.UserPrincipal;
import com.planchella.repositories.users.AuthUserRepository;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private AuthUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepo.findByUsername(username); // making a repo layer

        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }

}
