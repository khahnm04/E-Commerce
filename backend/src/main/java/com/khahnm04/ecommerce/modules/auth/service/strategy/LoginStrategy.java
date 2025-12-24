package com.khahnm04.ecommerce.modules.auth.service.strategy;

import com.khahnm04.ecommerce.modules.auth.dto.request.LoginRequest;
import com.khahnm04.ecommerce.modules.user.entity.User;

public interface LoginStrategy {

    boolean supports(LoginRequest request);
    User authenticate(LoginRequest request);

}
