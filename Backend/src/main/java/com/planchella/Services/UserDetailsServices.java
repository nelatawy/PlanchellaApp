package com.planchella.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.planchella.models.User;
import com.planchella.models.UserPrincipal;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User user = userRepo.findByUsername(username); // making a repo layer
        User user = new User();
        user.setId("12");
        user.setUsername("hamada");
        user.setPassword("pass");

        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }

}
