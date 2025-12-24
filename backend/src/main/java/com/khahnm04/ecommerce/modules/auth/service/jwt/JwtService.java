package com.khahnm04.ecommerce.modules.auth.service.jwt;

import com.khahnm04.ecommerce.modules.auth.dto.response.TokenPayload;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.nimbusds.jwt.SignedJWT;

public interface JwtService {

    TokenPayload generateAccessToken(User user);
    TokenPayload generateRefreshToken(User user);
    SignedJWT verifyAccessToken(String token);
    SignedJWT verifyRefreshToken(String token);

}
