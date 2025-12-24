package com.khahnm04.ecommerce.core.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security.cookie")
public class CookieProperties {

    private String domain;
    private boolean secure;
    private boolean httpOnly;
    private String sameSite;
    private Duration accessTokenMaxAge;
    private Duration refreshTokenMaxAge;

}
