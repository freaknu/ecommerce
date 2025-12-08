package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.config.JwtProvider;
import com.ecommerce.microservice.AuthService.dto.AuthResponse;
import com.ecommerce.microservice.AuthService.dto.LoginRequest;
import com.ecommerce.microservice.AuthService.dto.SignupRequest;
import com.ecommerce.microservice.AuthService.enums.Roles;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider provider;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse signUp(SignupRequest data,boolean isOAuthSignup) {
        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        AppUser user = new AppUser();
        user.setName(data.getName());
        user.setEmail(data.getEmail());

        if(isOAuthSignup) {
            user.setPassword("");
        }
        else user.setPassword(passwordEncoder.encode(data.getPassword()));

        user.setRoles(data.getRole());

        AppUser savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());

        String accessToken = provider.generateToken(userDetails);
        String refreshToken = provider.generateRefreshToken(userDetails);

        refreshTokenService.saveRefreshToken(savedUser, refreshToken);

        return new AuthResponse(savedUser.getId(),accessToken, refreshToken, savedUser.getRoles());
    }

    public AuthResponse login(LoginRequest data,boolean isOAuthSignIn) {

        AppUser user = userRepository
                .findByEmail(data.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

       if(!isOAuthSignIn) {
           if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
               throw new RuntimeException("Incorrect password");
           }
       }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String accessToken = provider.generateToken(userDetails);
        String refreshToken = provider.generateRefreshToken(userDetails);

        refreshTokenService.updateToken(user.getRefreshToken().getId(), refreshToken);

        return new AuthResponse(user.getId(),accessToken, refreshToken, user.getRoles());
    }

    public Boolean isValidToken(String token) {
        return provider.isValidToken(token);
    }

    public AuthResponse getTokens(String token) {
        String email = provider.isVaidRefreshToken(token);
        if(email == null) return new AuthResponse();
        AppUser user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = provider.generateToken(userDetails);
        String refreshToken = provider.generateRefreshToken(userDetails);

        refreshTokenService.updateToken(user.getRefreshToken().getId(), refreshToken);

        return new AuthResponse(user.getId(),accessToken, refreshToken, user.getRoles());
    }
}
