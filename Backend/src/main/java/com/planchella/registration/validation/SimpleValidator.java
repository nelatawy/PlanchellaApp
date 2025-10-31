package com.planchella.registration.validation;

public class SimpleValidator implements IValidator{

    @Override 
    public boolean isValidEmail(String email) {
        // Simple email validation logic
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    @Override
    public boolean isValidPassword(String password) {

        // Simple password validation logic
        return password != null && password.length() >= 6;
    }
    

    @Override 
    public boolean isValidUsername(String username) {
        // Simple username validation logic
        return username != null && !username.trim().isEmpty();
    }


}