package com.khahnm04.ecommerce.exception;

import lombok.Getter;
import java.util.Map;

@Getter
public class ServiceValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public ServiceValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public ServiceValidationException(String field, String message) {
        super(message);
        this.errors = Map.of(field, message);
    }

}
