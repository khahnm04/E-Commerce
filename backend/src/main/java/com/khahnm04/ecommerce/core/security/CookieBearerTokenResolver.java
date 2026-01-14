package com.khahnm04.ecommerce.core.security;

import com.khahnm04.ecommerce.shared.constant.TokenConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.WebUtils;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public String resolve(HttpServletRequest request) {

        String requestPath = request.getServletPath();

        for (String publicPath : SecurityConfig.PUBLIC_ENDPOINTS) {
            if (pathMatcher.match(publicPath, requestPath)) {
                return null;
            }
        }

        Cookie tokenCookie = WebUtils.getCookie(request, TokenConstants.ACCESS_TOKEN);
        return tokenCookie != null ? tokenCookie.getValue() : null;
    }

}
