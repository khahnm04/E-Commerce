package com.khahnm04.ecommerce.modules.auth.service.jwt;

import com.khahnm04.ecommerce.shared.constant.TokenConstants;
import com.khahnm04.ecommerce.modules.auth.dto.response.TokenPayload;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.auth.repository.RedisTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final RedisTokenRepository redisTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    @NonFinal
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @NonFinal
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public TokenPayload generateAccessToken(User user) {
        return generateToken(user, TokenConstants.ACCESS_TOKEN);
    }

    @Override
    public TokenPayload generateRefreshToken(User user) {
        return generateToken(user, TokenConstants.REFRESH_TOKEN);
    }

    @Override
    public SignedJWT verifyAccessToken(String token) {
        SignedJWT signedJWT = verifyToken(token);
        if (isExistTokenInRedis(signedJWT)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    @Override
    public SignedJWT verifyRefreshToken(String token) {
        SignedJWT signedJWT = verifyToken(token);
        if (!isExistTokenInRedis(signedJWT)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }
    
    private TokenPayload generateToken(User user, String typeToken) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String subject = StringUtils.hasText(user.getPhoneNumber()) ? user.getPhoneNumber() : user.getEmail();
        Date issueTime = new Date();
        Date expiredTime = new Date(Instant.now()
                .plus(typeToken.equalsIgnoreCase(TokenConstants.ACCESS_TOKEN)
                        ? accessTokenExpiration
                        : refreshTokenExpiration, ChronoUnit.SECONDS)
                .toEpochMilli());
        String jwtId = UUID.randomUUID().toString();

        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issueTime(issueTime)
                .expirationTime(expiredTime)
                .jwtID(jwtId)
                .claim("scope", buildScope(user))
                .claim("type", typeToken);

        if (typeToken.equalsIgnoreCase(TokenConstants.ACCESS_TOKEN)) {
            claimsBuilder.claim("userId", user.getId());
        }

        JWTClaimsSet jwtClaimsSet = claimsBuilder.build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new AppException(ErrorCode.TOKEN_CREATION_FAILED);
        }

        String token = jwsObject.serialize();
        return TokenPayload.builder()
                .jwtId(jwtId)
                .token(token)
                .expiredTime(expiredTime)
                .build();
    }

    private SignedJWT verifyToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            var verified = signedJWT.verify(verifier);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (!verified || !expiryTime.after(new Date())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT;
        } catch (ParseException | JOSEException e) {
            log.error("Cannot verify token", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        StringJoiner scope = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scope.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions()
                            .forEach(permission -> scope.add(permission.getName()));
                }
            });
        }
        return scope.toString();
    }

    private boolean isExistTokenInRedis(SignedJWT signedJWT) {
        try {
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            return redisTokenRepository.existsById(jwtId);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

}
