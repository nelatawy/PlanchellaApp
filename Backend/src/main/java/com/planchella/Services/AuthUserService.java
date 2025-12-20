package com.planchella.Services;

import com.planchella.domain.User;
import com.planchella.enums.AuthProvider;
import com.planchella.mappers.UserMapper;
import com.planchella.repositories.users.DBUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import com.planchella.entities.AuthUserEntity;
import com.planchella.repositories.users.AuthUserRepository;
import com.planchella.utils.IdGenerator;

@Service
public class AuthUserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private AuthUserRepository authRepo;

    @Autowired
    private DBUserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // @Value("${spring.security.oauth2.client.registration.google.client-id}")

    private String CLIENT_ID = "493505072228-l3nc8pvhbqjanr5gvmhepv4havsrr47u.apps.googleusercontent.com";

    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

    @Transactional
    public AuthUserEntity register(AuthUserEntity authData) {
        System.out.println("Lord have mercy");
        System.out.println("=== REGISTRATION START ===");
        System.out.println("Username: " + authData.getUsername());
        System.out.println("Email: " + authData.getEmail());

        User userData = new User();
        userData.setName(authData.getUsername());

        userData.setAccountUrl("http://localhost:4200/account/" + authData.getUsername()); // We should let the frontend
                                                                                           // not accept any spaces in
                                                                                           // the username
        userData.setPicUrl("https://ui-avatars.com/api/?name=" + authData.getUsername());

        userData.setEmail(authData.getEmail());

        // Generate and set ID for the domain User entity
        userData.setId(IdGenerator.generateId());

        Long id = userRepo.saveUser(userData);
        System.out.println("User saved with ID: " + id);

        // modify the data inside authData and return it
        authData.setId(IdGenerator.generateId()); // Generate ID for AuthUserEntity
        authData.setPassword(encoder.encode(authData.getPassword()));
        authData.setProvider(AuthProvider.LOCAL);
        authData.setProviderId(null);
        authData.setUserId(id);

        authRepo.save(authData);
        System.out.println("AuthUser saved - username: " + authData.getUsername());
        System.out.println("=== REGISTRATION END ===");
        return authData;
    }

    public String verify(AuthUserEntity user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public String verifyGoogleAuth(String token) throws Exception {
        System.out.println(CLIENT_ID);
        GoogleIdToken idToken = verifier.verify(token);

        if (idToken == null)
            throw new Exception("Null token provided for Google Auth");

        GoogleIdToken.Payload payload = idToken.getPayload();
        String googleId = payload.getSubject(); // permanent Google user ID (use this!)
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");
        Boolean emailVerified = payload.getEmailVerified();

        AuthUserEntity authUserData = authRepo.findByProviderId(googleId);
        if (authUserData == null)
            throw new Exception("User not found with this Google token");

        if (authUserData.getProviderId().equals(googleId) && emailVerified) {
            User userData = userRepo.getUser(authUserData.getUserId());
            userData.setEmail(email);
            userData.setName(name);
            userData.setPicUrl(picture);

            return jwtService.generateToken(googleId);
            // accept
        } else {
            throw new Exception("Email doesn't match our stored in database");
        }
    }

    public String registerGoogleAuth(String token) throws Exception {

        GoogleIdToken idToken = verifier.verify(token);

        if (idToken == null)
            throw new Exception("Null token provided for Google Auth");

        GoogleIdToken.Payload payload = idToken.getPayload();
        String googleId = payload.getSubject(); // permanent Google user ID (use this!)
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");
        Boolean emailVerified = payload.getEmailVerified();

        // check if user exists
        AuthUserEntity authUser = authRepo.findByProviderId(googleId);
        if (authUser != null) {
            throw new Exception("user already exists, can't register");
        }

        if (!emailVerified) {
            throw new Exception("Email is not verified");
        }

        User userData = new User();
        userData.setEmail(email);
        userData.setName(name);
        userData.setPicUrl(picture);
        userData.setId(IdGenerator.generateId()); // Generate ID for User
        Long id = userRepo.saveUser(userData);

        AuthUserEntity authUserData = new AuthUserEntity();
        authUserData.setId(IdGenerator.generateId()); // Generate ID for AuthUserEntity
        authUserData.setPassword(null);
        authUserData.setEmail(email);
        authUserData.setUsername(name);
        authUserData.setProvider(AuthProvider.GOOGLE);
        authUserData.setProviderId(googleId);
        authUserData.setUserId(id);

        authRepo.save(authUserData);

        System.out.println(email);
        System.out.println(name);

        return jwtService.generateToken(googleId);

    }
}
