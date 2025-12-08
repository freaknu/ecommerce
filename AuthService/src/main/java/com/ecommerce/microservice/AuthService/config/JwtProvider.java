package com.ecommerce.microservice.AuthService.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt_expiration}")
    private Long expiration;

    @Value("${refresh_token_expiration}")
    private Long refreshTokenExpiration;

    @Value("${otp_token_expiration}")
    private Long otpExpiration;

    private SecretKey getKey() {
        byte[] encodedKey = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(encodedKey);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts
                .builder()
                .signWith(getKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .subject(userDetails.getUsername())
                .claims(claims)
                .compact();
    }

    public String getUserName(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getKey())
                .subject(userDetails.getUsername())
                .compact();
    }
    public String isVaidRefreshToken(String token) {
        boolean isNotExpired = Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .after(new Date());

        if(!isNotExpired) return null;

        return
                Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                        .getSubject();
    }
    public String generateOtpToken(String email) {
        return Jwts
                .builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + otpExpiration))
                .signWith(getKey())
                .subject(email)
                .compact();

    }

    public boolean isValidOtpToken(String token,String email) {
        boolean isExpired =
                Jwts
                        .parser()
                        .verifyWith(getKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration().before(new Date());
        if(isExpired) return false;
        String subject = Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return subject.equals(email);
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return (List<String>) claims.getOrDefault("roles", Collections.emptyList());
    }
}
