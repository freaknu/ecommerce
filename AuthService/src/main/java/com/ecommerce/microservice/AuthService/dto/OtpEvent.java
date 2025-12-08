package com.ecommerce.microservice.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OtpEvent {
    private int otp;
    private String message;
    private String email;
}
