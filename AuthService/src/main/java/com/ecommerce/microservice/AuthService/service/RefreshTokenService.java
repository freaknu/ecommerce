package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.config.JwtProvider;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.model.RefreshToken;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import com.ecommerce.microservice.AuthService.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtProvider provider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserRepository userRepository;

    public String generateRefreshToken(UserDetails userDetails) {
        return provider.generateRefreshToken(userDetails);
    }

    @Transactional
    public void saveRefreshToken(AppUser savedUser, String refreshToken) {
        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusDays(50));
        token.setUpdatedAt(LocalDateTime.now());
        token.setAppUser(savedUser);

        refreshTokenRepository.save(token);
    }

    @Transactional
    public void updateToken(Long tokenId, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findById(tokenId).get();
        token.setToken(refreshToken);
        token.setUpdatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusDays(50));
        refreshTokenRepository.save(token);
    }

}
