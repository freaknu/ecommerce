package com.ecommerce.maingateway.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtHeaderForwardingInterceptor implements HandlerInterceptor {

    private final JwtDecoder jwtDecoder;
    private final RedisTemplate<String, String> redisTemplate;

    private static final int LIMIT = 5;
    private static final int WINDOW_SECOND = 50;

    private final Logger logger =
            LoggerFactory.getLogger(JwtHeaderForwardingInterceptor.class);

    private final List<String> rateLimitedPaths = List.of(
            "/createCategory",
            "/createProduct",
            "/updateProduct",
            "/updateCategory",
            "/order/pay",
            "/placeOrder"
    );

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();

        if (uri.startsWith("/swagger")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/swagger-resources")
                || uri.startsWith("/webjars")
                || uri.startsWith("/aggregate")) {
            return true;
        }

        if(uri.startsWith("/api/auth")) return true;

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }

        String token = authHeader.substring(7);
        Jwt jwt = jwtDecoder.decode(token);

        Long userId = jwt.getClaim("user_id");
        String roles = jwt.getClaimAsString("roles");

        request.setAttribute("X-User-Id", userId);
        request.setAttribute("X-User-Roles", roles);


        boolean shouldRateLimit = rateLimitedPaths
                .stream()
                .anyMatch(uri::contains);

        if (shouldRateLimit) {

            String key = "rate:" + uri + ":" + userId;

            Long count = redisTemplate.opsForValue().increment(key);

            if (count != null && count == 1) {
                redisTemplate.expire(key, WINDOW_SECOND, TimeUnit.SECONDS);
            }

            logger.info("Rate count {} = {}", key, count);

            if (count != null && count > LIMIT) {
                response.setStatus(429);
                response.getWriter().write("Rate limit exceeded");
                return false;
            }
        }

        return true;
    }
}
