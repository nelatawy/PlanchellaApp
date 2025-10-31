package com.planchella.registration.validation;

public class MockValidator implements IValidator {

    @Override
    public ValidationResult checkEmail(String email) {
        return ValidationResult.validResult();
    }
    @Override

    public ValidationResult checkPassword(String userName) {

        return ValidationResult.validResult();
    }
    @Override
    public ValidationResult checkUsername(String email) {
        return ValidationResult.validResult();
    }
    
}
