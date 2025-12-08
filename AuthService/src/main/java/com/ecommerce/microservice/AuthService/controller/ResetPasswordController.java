package com.ecommerce.microservice.AuthService.controller;

import com.ecommerce.microservice.AuthService.service.ForgetPasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ResetPassword Controller",description = "ResetPasswordController for Ecommerce")
@RequestMapping("/api/auth")
public class ResetPasswordController {
    private final ForgetPasswordService passwordService;

    @PostMapping("/sendVerificationCode/{email}")
    public ResponseEntity<String> sendCode(@PathVariable String email) {
        try {
            passwordService.sendVerificationCode(email);
            return ResponseEntity.ok("Otp Sent Successfully");
        }
        catch (Exception e) {
            log.error("Problem while sending the otp",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtpToken(@PathVariable Integer otp,@PathVariable String email) {
        try{
            String res = passwordService.verifyToken(otp,email);
            if(res == null) {
                return ResponseEntity.badRequest().body("Something went wrong");
            }

            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while verifying the otp",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/changePassword/{token}/{newPassword}/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email,@PathVariable String token,@PathVariable String newPassword) {
        try {
            var res = passwordService.changePassword(email,token,newPassword);
            if(!res) return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok().body("Password Changed Successfully");
        }
        catch (Exception e) {
            log.error("Problem while changing the password",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

}
