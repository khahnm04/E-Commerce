package com.khahnm04.ecommerce.common.validation.upload;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, Object> {

    @Override
    public void initialize(NotEmptyFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value instanceof MultipartFile file) {
            return !file.isEmpty();
        }

        if (value instanceof List<?> list) {
            if (list.isEmpty()) return false;
            for (Object item : list) {
                if (item instanceof MultipartFile file) {
                    if (file.isEmpty()) return false;
                }
            }
            return true;
        }
        return false;
    }

}
