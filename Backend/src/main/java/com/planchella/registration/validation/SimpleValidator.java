package com.planchella.registration.validation;


import java.util.*;

public class SimpleValidator implements IValidator{


    private boolean hasSpecialChars(String str) {
        return str.matches(".*[!@#$%^&*()].*");
    }

    @Override 
    public ValidationResult checkEmail(String email) {
        List<ValidationErrors> errors = new LinkedList<>();
        // Simple email validation logic
        if (email == null) {
            errors.add(ValidationErrors.NULL_FIELD);
            return ValidationResult.invalidResult(errors);
        }
        if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            return ValidationResult.validResult();
        }
        else {
            errors.add(ValidationErrors.EMAIL_FORM);
            return ValidationResult.invalidResult(errors);
        }
    }

    @Override
    public  ValidationResult checkPassword(String password) {
        
        List<ValidationErrors> errors = new LinkedList<>();
        
        if (password == null) {
            errors.add(ValidationErrors.NULL_FIELD);
            return ValidationResult.invalidResult(errors);
        }


        boolean longEnough = password.length() >= 6;
        if (!longEnough) {
            errors.add(ValidationErrors.PASSWORD_LENGTH);
        }

        boolean isCaseDiverse = password.matches(".*[a-z].*") && password.matches(".*[A-Z].*");
        if (!isCaseDiverse) {
            errors.add(ValidationErrors.PASSWORD_CASE_DIVERSITY);
        }

        boolean hasDigit = password.matches(".*\\d.*");
        if (!hasDigit) {
            errors.add(ValidationErrors.PASSWORD_DIGIT_INCLUSION);
        }
        boolean hasSpecialChar = hasSpecialChars(password);
        if (!hasSpecialChar) {
            errors.add(ValidationErrors.PASSWORD_SPECIALS_INCLUSION);
        }


        if (longEnough && isCaseDiverse && hasDigit && hasSpecialChar) {
            return ValidationResult.validResult();
        } else {
            return ValidationResult.invalidResult(errors);
        }
    }

    @Override 
    public ValidationResult checkUsername(String username) {
        // Simple username validation logic
        List<ValidationErrors> errors = new LinkedList<>();
        
        if (username == null) {
            errors.add(ValidationErrors.NULL_FIELD);
            return ValidationResult.invalidResult(null);
        }
        
        
        boolean hasSpaces = username.contains(" ");
        if (hasSpaces) {
            errors.add(ValidationErrors.USERNAME_HAS_SPACES);
        }
        
        boolean correctLength = username.length() >= 3 && username.length() <= 15;
        if (!correctLength) {
            errors.add(ValidationErrors.USERNAME_LENGTH);
        }
        
        boolean hasSpecialChars = hasSpecialChars(username);
        if (hasSpecialChars) {
            errors.add(ValidationErrors.USERNAME_SPECIALS_INCLUSIONS);
        }

        if (!hasSpecialChars && correctLength && !hasSpaces) {
            return ValidationResult.validResult();
        } else {
            return ValidationResult.invalidResult(errors);
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IValidator validator = new SimpleValidator();
        String email = sc.nextLine(), password = sc.nextLine(), username = sc.nextLine();
        
        System.out.println("Email Valid : " + validator.checkEmail(email).isValid());
        System.out.println("Password Valid : " + validator.checkPassword(password).isValid());
        System.out.println("Username Valid : " + validator.checkUsername(username).isValid());
        sc.close();
    }

}