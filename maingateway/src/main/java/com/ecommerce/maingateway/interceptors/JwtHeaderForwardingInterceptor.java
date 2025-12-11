package com.ecommerce.maingateway.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.Jwt;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtHeaderForwardingInterceptor implements HandlerInterceptor {

    private final JwtDecoder jwtDecoder;
    private final Logger logger = LoggerFactory.getLogger(JwtHeaderForwardingInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Jwt jwt = jwtDecoder.decode(token);
            logger.info("the role of user is  : {}",jwt.getClaimAsString("roles"));
            request.setAttribute("X-User-Roles", jwt.getClaimAsString("roles"));
        }
        return true;
    }
}
