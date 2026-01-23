package com.ecommerce.microservice.AuthService.controller;

import com.ecommerce.microservice.AuthService.common.ApiResponseFormat;
import com.ecommerce.microservice.AuthService.dto.AuthResponse;
import com.ecommerce.microservice.AuthService.dto.LoginRequest;
import com.ecommerce.microservice.AuthService.dto.SignupRequest;
import com.ecommerce.microservice.AuthService.service.AuthService;
import com.ecommerce.microservice.AuthService.service.OauthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Authentication For Ecommerce")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OauthService oauthService;

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponseFormat<AuthResponse>> createAccount(
            @RequestBody SignupRequest data
    ) {
        AuthResponse res = authService.signUp(data, false);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "User created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseFormat<AuthResponse>> login(
            @RequestBody LoginRequest data
    ) {
        AuthResponse res = authService.login(data, false);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Login successful",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/valid-token/{token}")
    public ResponseEntity<ApiResponseFormat<Boolean>> isValid(
            @PathVariable String token
    ) {
        Boolean res = authService.isValidToken(token);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Token validation result",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getByRefreshToken/{token}")
    public ResponseEntity<ApiResponseFormat<AuthResponse>> generateByRefreshToken(
            @PathVariable String token
    ) {
        AuthResponse res = authService.getTokens(token);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "New tokens generated",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<ApiResponseFormat<AuthResponse>> googleSuccess(
            @AuthenticationPrincipal OAuth2User user
    ) {
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    new ApiResponseFormat<>(
                            null,
                            "OAuth2 authentication failed",
                            false,
                            HttpStatus.BAD_REQUEST.value(),
                            LocalDateTime.now()
                    )
            );
        }

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        AuthResponse response = oauthService.generateToken(name, email);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        response,
                        "OAuth2 login successful",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
