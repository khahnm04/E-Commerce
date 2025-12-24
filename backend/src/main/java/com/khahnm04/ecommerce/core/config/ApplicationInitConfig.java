package com.khahnm04.ecommerce.core.config;

import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.shared.enums.UserRole;
import com.khahnm04.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return app -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(UserRole.ADMIN.name());

                User user = new User();
                user.setFullName("admin");
                user.setPhoneNumber("0812345678");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin"));

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }

}
