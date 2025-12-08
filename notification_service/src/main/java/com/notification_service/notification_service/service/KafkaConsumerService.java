package com.notification_service.notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_service.notification_service.events.OrderPlacedEvent;
import com.notification_service.notification_service.events.OtpEvent;
import com.notification_service.notification_service.model.UserFcmTokenDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;
    private final DetailsService userDetails;

    @KafkaListener(topics = "order_topic", groupId = "notificationService")
    public void listenOrderPlaced(Map<String, Object> orderEvent) {

        Long userId = Long.valueOf(orderEvent.get("userId").toString());
        Long orderId = Long.valueOf(orderEvent.get("orderId").toString());

        log.info("Received OrderPlacedEvent for userId: {}", userId);

        var userDetailsResponse = userDetails.getByUserId(userId);
        String email = userDetailsResponse.getEmail();

        emailService.sendEmail(
                email,
                "Order Placed Successfully!",
                "Your order with ID " + orderId + " has been placed."
        );
        log.info("Order email sent to {}", email);
    }


    @KafkaListener(topics = "otp_event", groupId = "notificationService")
    public void listenOtpEvent(Map<String, Object> payload) {

        int otp = (int) payload.get("otp");
        String email = (String) payload.get("email");
        String message = (String) payload.get("message");

        log.info("Received OTP {} for {}", otp, email);

        emailService.sendEmail(
                email,
                "Reset Password OTP for Laza Ecommerce Application",
                "Your OTP is: " + otp
        );
    }

}