package com.khahnm04.ecommerce.shared.validation.password;

import com.khahnm04.ecommerce.shared.util.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecialChar;

    @Override
    public void initialize(ValidPassword annotation) {
        this.minLength = annotation.minLength();
        this.requireUppercase = annotation.requireUppercase();
        this.requireLowercase = annotation.requireLowercase();
        this.requireDigit = annotation.requireDigit();
        this.requireSpecialChar = annotation.requireSpecialChar();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (!StringUtils.hasText(password)) {
            return true;
        }

        // Length checks
        if (password.length() < minLength) {
            ValidationUtils.buildError("Password must be at least 8 characters", context);
            return false;
        }

        // Uppercase check
        if (requireUppercase && password.chars().noneMatch(Character::isUpperCase)) {
            ValidationUtils.buildError("Password must contain at least one uppercase letter", context);
            return false;
        }

        // Lowercase check
        if (requireLowercase && password.chars().noneMatch(Character::isLowerCase)) {
            ValidationUtils.buildError("Password must contain at least one lowercase letter", context);
            return false;
        }

        // Digit check
        if (requireDigit && password.chars().noneMatch(Character::isDigit)) {
            ValidationUtils.buildError("Password must contain at least one digit", context);
            return false;
        }

        // Special character check
        if (requireSpecialChar && !password.matches(".*[^A-Za-z0-9].*")) {
            ValidationUtils.buildError("Password must contain at least one special character", context);
            return false;
        }

        return true;
    }

}
