package com.ecommerce.microservice.order_service.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {
    private final Logger log = LoggerFactory.getLogger(RoleAspect.class);
    private final HttpServletRequest request;

    @Before("@annotation(roleAnnotation)")
    public void checkRole(JoinPoint jp, RoleAnnotation roleAnnotation) {

        String userRolesHeader = request.getHeader("X-User-Roles");

        if (userRolesHeader == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No roles found in request");
        }

        List<String> roles = Arrays.asList(userRolesHeader
                .replace("[", "")
                .replace("]", "")
                .split(","));

        String requiredRole = roleAnnotation.value();

        if (!roles.contains(requiredRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access denied! Required role: " + requiredRole);
        }

        log.info("Role Check OK → Allowed: " + roles);
    }
}
