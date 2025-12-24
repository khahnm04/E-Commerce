package com.khahnm04.ecommerce.shared.util;

public class PhoneNumberUtils {

    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        String normalized = phoneNumber.replaceAll(" ", "");
        if (normalized.startsWith("+84")) {
            normalized = normalized.replaceFirst("\\+84", "0");
        }
        return normalized;
    }

}
