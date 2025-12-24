package com.khahnm04.ecommerce.shared.util;

import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtils {

    public static void buildError(String errorCode, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode)
                .addConstraintViolation();
    }

}
