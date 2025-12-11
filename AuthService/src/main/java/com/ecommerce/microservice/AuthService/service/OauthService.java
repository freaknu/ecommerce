package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.config.JwtProvider;
import com.ecommerce.microservice.AuthService.dto.AuthResponse;
import com.ecommerce.microservice.AuthService.dto.LoginRequest;
import com.ecommerce.microservice.AuthService.dto.SignupRequest;
import com.ecommerce.microservice.AuthService.enums.Roles;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class OauthService {

    private final AppUserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    public AuthResponse generateToken(String name, String email) {

        AppUser user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {

            user = new AppUser();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(UUID.randomUUID().toString());
            Set<Roles> roles = new HashSet<>();
            roles.add(Roles.USER);

            user.setRoles(roles);

            user = userRepository.save(user);
        }

        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password("")
                .authorities(authorities)
                .build();

        String access = jwtProvider.generateToken(userDetails);
        String refresh = jwtProvider.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .userId(user.getId())
                .accessToken(access)
                .refreshToken(refresh)
                .roles(user.getRoles())
                .build();
    }
}
