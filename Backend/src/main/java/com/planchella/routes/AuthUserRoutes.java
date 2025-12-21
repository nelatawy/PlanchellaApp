package com.planchella.routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.planchella.Services.AuthUserService;
import com.planchella.entities.AuthUserEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthUserRoutes {
    @Autowired
    private AuthUserService service;

    @PostMapping("/register")
    public AuthUserEntity register(@RequestBody AuthUserEntity user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthUserEntity user) {
        return service.verify(user);
    }

    @PostMapping("/auth/google/register")
    public String handleGoogleAuthRegister(@RequestBody String token) {
        try {
            System.out.println(token);
            return service.registerGoogleAuth(token);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "ERR";
        }
    }

    @PostMapping("/auth/google/login")
    public ResponseEntity<?> handleGoogleAuthLogin(@RequestBody String token) {
        try {
            System.out.println(token);
            return ResponseEntity.ok(service.verifyGoogleAuth(token));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
