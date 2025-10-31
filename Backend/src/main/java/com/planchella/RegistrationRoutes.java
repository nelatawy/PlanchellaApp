package com.planchella;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planchella.registration.validation.IValidator;
import com.planchella.registration.validation.SimpleValidator;
import com.planchella.registration.validation.ValidationResult;

@Controller
@RequestMapping("/register")
public class RegistrationRoutes {
    
    @PostMapping(path = "/check")
    public boolean checkValidity(@RequestAttribute String email) {
        IValidator validator = new SimpleValidator();
        ValidationResult res = validator.checkEmail(email);
        return res.isValid();
    }

    

}
