package com.khahnm04.ecommerce.shared.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

@Slf4j
public class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Lấy thông tin định danh (principal) của người dùng hiện tại từ SecurityContext.
     * Có thể là UserDetails, subject (Jwt) hoặc String.
     * @return thông tin principal của người dùng hoặc null nếu không có
     */
    public static String extractPrincipal() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return null;
            }
            if(authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            }
            if(authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getSubject();
            }
            if (authentication.getPrincipal() instanceof  String s) {
                return s;
            }
        } catch (Exception e) {
            log.error("Error getting current user", e);
        }
        return null;
    }

}
