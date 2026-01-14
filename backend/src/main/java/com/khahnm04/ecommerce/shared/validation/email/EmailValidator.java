package com.khahnm04.ecommerce.shared.validation.email;

import com.khahnm04.ecommerce.shared.util.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (StringUtils.hasText(email) && !email.matches(EMAIL_REGEX)) {
            ValidationUtils.buildError("Email format is invalid", context);
            return false;
        }
        return true;
    }

}
