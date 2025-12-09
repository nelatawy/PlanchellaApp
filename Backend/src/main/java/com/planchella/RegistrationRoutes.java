package com.planchella;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planchella.registration.validation.IValidator;
import com.planchella.registration.validation.SimpleValidator;
import com.planchella.registration.validation.ValidationResult;

@RestController
@RequestMapping("/register")
public class RegistrationRoutes {

    @PostMapping(path = "/check")
    public boolean checkValidity(@RequestParam String email) {
        IValidator validator = new SimpleValidator();
        ValidationResult res = validator.checkEmail(email);
        return res.isValid();
    }

}
