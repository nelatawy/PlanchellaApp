package com.planchella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planchella.Services.AuthUserService;
import com.planchella.domain.AuthUser;

@RestController
public class AuthUserRoutes {
    @Autowired
    private AuthUserService service;

    @PostMapping("/register")
    public AuthUser register(@RequestBody AuthUser user) {
        return service.register(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody AuthUser user) {

        return service.verify(user);
    }
}
