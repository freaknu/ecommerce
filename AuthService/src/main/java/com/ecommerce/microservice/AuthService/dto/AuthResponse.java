package com.ecommerce.microservice.AuthService.dto;


import com.ecommerce.microservice.AuthService.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Set<Roles> roles;
}
