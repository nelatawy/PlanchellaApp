package com.planchella.registration.validation;

public class MockValidator implements IValidator {

    @Override
    public boolean isValidEmail(String email) {
        return true;
    }
    @Override

    public boolean isValidUsername(String userName) {

        return true;
    }
    @Override
    public boolean isValidPassword(String email) {
        return true;
    }
    
}
