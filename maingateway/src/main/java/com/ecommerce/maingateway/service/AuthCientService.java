package com.ecommerce.maingateway.service;

import com.ecommerce.maingateway.config.AuthServiceClient;
import com.ecommerce.maingateway.dto.UserPermissionDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCientService {
    private final AuthServiceClient client;
    public UserPermissionDetailsDto getDetails(String token) {
        return (UserPermissionDetailsDto) client.getCurrentUser(token);
    }
}
