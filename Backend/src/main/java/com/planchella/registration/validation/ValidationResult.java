package com.planchella.registration.validation;

import java.util.*;

enum ValidationErrors{
    NULL_FIELD,
    EMAIL_FORM ,

    // Password issues
    PASSWORD_LENGTH ,
    PASSWORD_CASE_DIVERSITY,
    PASSWORD_SPECIALS_INCLUSION,
    PASSWORD_DIGIT_INCLUSION,

    // Username issues
    USERNAME_VALIDITY,
    USERNAME_HAS_SPACES,
    USERNAME_LENGTH,
    USERNAME_SPECIALS_INCLUSIONS

}

public class ValidationResult {
    boolean valid ;
    List<ValidationErrors> errors;
    
    // Private constructor to hide this logic
    private ValidationResult(boolean valid, List<ValidationErrors> errors){
        this.valid = valid;
        this.errors = errors;
    }

    // Get a new Valid ValidationResult instance
    public static ValidationResult validResult() {
        return new ValidationResult(true, null);
    }

    // Get a new Invalid ValidationResult instance
    public static ValidationResult invalidResult(List<ValidationErrors> errors) {
        return new ValidationResult(false, errors);
    }

    public boolean isValid() {
        return this.valid;
    }
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

}
