package com.ecommerce.microservice.AuthService.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/detail")
@Slf4j
public class UserDetailsExtractor {
    @GetMapping("/getCurrentUser")
    public ResponseEntity<?> getCurrentUser() {
        try {
            var context = SecurityContextHolder.getContext().getAuthentication();
            String email = context.getName();
            String role = context.getAuthorities().toString();
            Map<String,String> res = new HashMap<>();
            res.put("Email",email);
            res.put("Roles",role);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Unable while getting the user details");
            return ResponseEntity.internalServerError().build();
        }
    }
}
