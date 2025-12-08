package com.ecommerce.microservice.AuthService.event;

import com.ecommerce.microservice.AuthService.dto.OtpEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaEvent {
    private final KafkaTemplate<String,OtpEvent> template;
    public void sendOtp(OtpEvent event) {
        log.info("sending otp");
        template.send("otp_event",event);
        log.info("otp sent");
    }
}
