package com.khahnm04.ecommerce.shared.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryStatus {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String value;

    CategoryStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static CategoryStatus fromString(String value) {
        if (value == null) return null;
        for (CategoryStatus s : values()) {
            if (s.value.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }

}
