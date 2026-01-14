package com.khahnm04.ecommerce.modules.auth.service;

import com.khahnm04.ecommerce.modules.auth.dto.request.LoginRequest;
import com.khahnm04.ecommerce.modules.auth.dto.request.RegisterRequest;
import com.khahnm04.ecommerce.modules.auth.dto.response.LoginResponse;
import com.khahnm04.ecommerce.modules.auth.dto.response.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request, HttpServletResponse response);
    void logout(String accessToken, String refreshToken, HttpServletResponse response);
    void refreshToken(String token, HttpServletResponse response);
    RegisterResponse register(RegisterRequest request);

}
