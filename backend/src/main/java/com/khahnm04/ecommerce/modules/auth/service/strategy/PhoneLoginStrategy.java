package com.khahnm04.ecommerce.modules.auth.service.strategy;

import com.khahnm04.ecommerce.modules.auth.dto.request.LoginRequest;
import com.khahnm04.ecommerce.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class PhoneLoginStrategy implements LoginStrategy {

    private final AuthenticationManager authenticationManager;

    @Override
    public boolean supports(LoginRequest request) {
        return StringUtils.hasText(request.getPhoneNumber());
    }

    @Override
    public User authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        return (User) authentication.getPrincipal();
    }

}
