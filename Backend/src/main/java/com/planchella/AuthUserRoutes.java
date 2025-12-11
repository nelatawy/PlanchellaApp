package com.planchella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.planchella.Services.AuthUserService;
import com.planchella.domain.AuthUser;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthUserRoutes {
    @Autowired
    private AuthUserService service;

    @PostMapping("/register")
    public AuthUser register(@RequestBody AuthUser user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public Map<String,String > login(@RequestBody AuthUser user) {
        Map<String, String> response = new HashMap<>();

        String auth_token =  service.verify(user);
        response.put("token", auth_token);
        return response;
    }
}
