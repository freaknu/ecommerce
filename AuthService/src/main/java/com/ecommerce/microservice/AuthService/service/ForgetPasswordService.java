package com.ecommerce.microservice.AuthService.service;

import com.ecommerce.microservice.AuthService.config.JwtProvider;
import com.ecommerce.microservice.AuthService.dto.OtpEvent;
import com.ecommerce.microservice.AuthService.event.KafkaEvent;
import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.model.Otps;
import com.ecommerce.microservice.AuthService.repository.AppUserRepository;
import com.ecommerce.microservice.AuthService.repository.OtpRepository;
import com.ecommerce.microservice.AuthService.utils.GenerateOtp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class ForgetPasswordService {

    private final AppUserRepository userRepository;
    private final OtpRepository otpRepository;
    private final KafkaEvent event;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public void sendVerificationCode(String email) throws Exception {
        try {
            System.out.println("the email is " + email);
            AppUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new Exception("User does not exist: " + email));

            int otp = GenerateOtp.generateOtp();
            System.out.println("the otp is " + otp);
            Otps newOtp = new Otps();
            newOtp.setOtp(otp);
            newOtp.setUsed(false);
            newOtp.setSentAt(LocalDateTime.now());
            newOtp.setUser(user);

            otpRepository.save(newOtp);

            OtpEvent otpEvent = new OtpEvent();
            otpEvent.setOtp(otp);
            otpEvent.setEmail(user.getEmail());
            otpEvent.setMessage("OTP for verification");

            event.sendOtp(otpEvent);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean verifyOtp(int otp, String email) throws Exception {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found: " + email));

        Otps ot = user.getAllOtps()
                .stream()
                .filter(o -> o.getOtp().equals(otp))
                .findFirst()
                .orElse(null);

        if (ot == null) return false;

        if (ot.isUsed()) return false;


        if (ot.getSentAt().plusMinutes(10).isBefore(LocalDateTime.now()))
            return false;

        ot.setUsed(true);
        otpRepository.save(ot);

        return true;
    }

    public String verifyToken(int otp,String email) throws Exception {
        if(!verifyOtp(otp,email)) return null;
        return jwtProvider.generateOtpToken(email);
    }

    public boolean changePassword(String email,String token,String newPassword) {
        boolean res = jwtProvider.isValidOtpToken(token,email);
        if(!res) return false;

        AppUser user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
