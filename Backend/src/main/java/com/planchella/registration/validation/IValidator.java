package com.planchella.registration.validation;

public interface IValidator {
    
    boolean isValidUsername(String userName);

    boolean isValidEmail(String email);

    boolean isValidPassword(String password);


}