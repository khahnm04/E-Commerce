package com.khahnm04.ecommerce.shared.validation.phone;

import com.khahnm04.ecommerce.shared.util.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final String PHONE_REGEX = "^(?:\\+84|0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(phoneNumber)) {
            return true;
        }
        String normalized = phoneNumber.replaceAll(" ", "");
        if (normalized.startsWith("+84")) {
            normalized = normalized.replaceFirst("\\+84", "0");
        }
        if (normalized.length() != 10) {
            ValidationUtils.buildError("Phone number must be exactly 10 digits", context);
            return false;
        }
        if (!normalized.matches(PHONE_REGEX)) {
            ValidationUtils.buildError("Phone number format is invalid", context);
            return false;
        }
        return true;
    }

}
