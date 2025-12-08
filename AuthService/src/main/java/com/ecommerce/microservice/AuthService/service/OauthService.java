package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.config.JwtProvider;
import com.ecommerce.microservice.AuthService.dto.AuthResponse;
import com.ecommerce.microservice.AuthService.dto.LoginRequest;
import com.ecommerce.microservice.AuthService.dto.SignupRequest;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final AppUserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    public AuthResponse generateToken(String name, String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElse(null);
        if(user == null) {
            SignupRequest data = new SignupRequest();
            data.setName(name);
            data.setEmail(email);
            data.setRole(null);
            data.setPassword(null);
            return authService.signUp(data,true);
        }

        LoginRequest data = new LoginRequest();
        data.setEmail(email);
        data.setPassword(null);
        return authService.login(data,true);
    }
}
