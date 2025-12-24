package com.khahnm04.ecommerce.core.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Optional;
import com.khahnm04.ecommerce.modules.user.entity.User;

@Slf4j
@Component
public class AuditorAwareConfig implements AuditorAware<Long> {

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof Jwt jwt) {
                Long userId = jwt.getClaim("userId");
                return Optional.of(userId);
            }

            if (principal instanceof User user) {
                return Optional.of(user.getId());
            }

            return Optional.empty();

        } catch (Exception e) {
            log.warn("The current auditor could not be determined. Error: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
