package com.khahnm04.ecommerce.shared.validation.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidEnum enumValue) {
        acceptedValues = Stream.of(enumValue.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null)  return true;
        boolean valid = acceptedValues.contains(value.toString().toUpperCase());
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid value '" + value + "'. Must be one of " + acceptedValues)
                    .addConstraintViolation();
        }
        return valid;
    }

}
