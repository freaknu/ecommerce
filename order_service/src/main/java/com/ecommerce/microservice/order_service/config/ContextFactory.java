package com.ecommerce.microservice.order_service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextFactory {

    @Autowired
    private HttpServletRequest request;

    public void getRoles() {
        String roles = request.getHeader("X-User-Roles");
        System.out.println("Roles from gateway = " + roles);
    }
}
