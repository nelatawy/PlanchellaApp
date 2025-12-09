package com.planchella.registration.validation;

public interface IValidator {
    
    ValidationResult checkUsername(String userName);

    ValidationResult checkEmail(String email);

    ValidationResult checkPassword(String password);

}