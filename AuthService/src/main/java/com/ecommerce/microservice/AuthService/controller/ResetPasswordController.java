package com.ecommerce.microservice.AuthService.controller;

import com.ecommerce.microservice.AuthService.common.ApiResponseFormat;
import com.ecommerce.microservice.AuthService.service.ForgetPasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ResetPassword Controller", description = "ResetPasswordController for Ecommerce")
@RequestMapping("/api/auth")
public class ResetPasswordController {

    private final ForgetPasswordService passwordService;

    @PostMapping("/sendVerificationCode/{email}")
    public ResponseEntity<ApiResponseFormat<String>> sendCode(
            @PathVariable String email
    ) throws Exception {
        passwordService.sendVerificationCode(email);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        null,
                        "OTP sent successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<ApiResponseFormat<String>> verifyOtpToken(
            @PathVariable Integer otp,
            @PathVariable String email
    ) throws Exception {
        String res = passwordService.verifyToken(otp, email);

        if (res == null) {
            return ResponseEntity.badRequest().body(
                    new ApiResponseFormat<>(
                            null,
                            "Invalid OTP or email",
                            false,
                            HttpStatus.BAD_REQUEST.value(),
                            LocalDateTime.now()
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "OTP verified successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/changePassword/{token}/{newPassword}/{email}")
    public ResponseEntity<ApiResponseFormat<String>> changePassword(
            @PathVariable String email,
            @PathVariable String token,
            @PathVariable String newPassword
    ) {
        boolean res = passwordService.changePassword(email, token, newPassword);

        if (!res) {
            return ResponseEntity.badRequest().body(
                    new ApiResponseFormat<>(
                            null,
                            "Invalid token or email",
                            false,
                            HttpStatus.BAD_REQUEST.value(),
                            LocalDateTime.now()
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        null,
                        "Password changed successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
