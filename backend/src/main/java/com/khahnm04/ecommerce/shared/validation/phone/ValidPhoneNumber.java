package com.khahnm04.ecommerce.shared.validation.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "phoneNumber is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
