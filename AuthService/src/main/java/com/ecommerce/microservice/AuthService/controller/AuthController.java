package com.ecommerce.microservice.AuthService.controller;


import com.ecommerce.microservice.AuthService.dto.AuthResponse;
import com.ecommerce.microservice.AuthService.dto.LoginRequest;
import com.ecommerce.microservice.AuthService.dto.SignupRequest;
import com.ecommerce.microservice.AuthService.service.AuthService;
import com.ecommerce.microservice.AuthService.service.OauthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Controller",description = "Authentication For Ecommerce")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final OauthService oauthService;

    @PostMapping("/create-user")
    public ResponseEntity<AuthResponse> createAccount(@RequestBody SignupRequest data) throws Exception {
        try {
            AuthResponse res = authService.signUp(data,false);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest data) throws Exception {
        try {
            AuthResponse res = authService.login(data,false);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @GetMapping("/valid-token/{token}")
    public ResponseEntity<Boolean> isValid(@PathVariable String token)throws Exception {
        var res = authService.isValidToken(token);
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/getByRefreshToken/{token}")
    public ResponseEntity<AuthResponse> generateByRefreshToken(@PathVariable String token) {
        try {
            var res = authService.getTokens(token);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> googleSuccess(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) return ResponseEntity.badRequest().build();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        AuthResponse response = oauthService.generateToken(name, email);

        return ResponseEntity.ok(response);
    }
}
