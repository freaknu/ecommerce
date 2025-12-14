package com.notification_service.notification_service.service;

import com.notification_service.notification_service.events.OrderPlacedEvent;
import com.notification_service.notification_service.events.OtpEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;
    private final DetailsService userDetails;

    @RetryableTopic(
            attempts = "5",
            backOff = @BackOff(delay = 2000L, multiplier = 2.0),
            autoCreateTopics = "true",
            exclude = {DeserializationException.class}
    )
    @KafkaListener(topics = "order_topic", groupId = "notificationService",
    properties = {
            "spring.json.value.default.type=com.notification_service.notification_service.events.OrderPlacedEvent"
    })
    public void listenOrderPlaced(@Payload OrderPlacedEvent event,
                                  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received OrderPlacedEvent from {}: userId={}, orderId={}",
                topic, event.getUserId(), event.getOrderId());


        var userDetailsResponse = userDetails.getByUserId(event.getUserId());
        String email = userDetailsResponse.getEmail();
        emailService.sendEmail(email, "Order Placed Successfully!",
                "Your order with ID " + event.getOrderId() + " has been placed.");
        log.info("Order confirmation email sent to {}", email);
    }

    @RetryableTopic(
            attempts = "4",
            backOff = @BackOff(delay = 1000L, multiplier = 2.0),
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = "otp_event", groupId = "notificationService",
    properties = {
            "spring.json.value.default.type=com.notification_service.notification_service.events.OtpEvent"
    })
    public void listenOtpEvent(@Payload OtpEvent event,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received OtpEvent from {} for {}", topic, event.getEmail());
        emailService.sendEmail(event.getEmail(), "Reset Password OTP",
                "Your OTP is: " + event.getOtp() +
                        (event.getMessage() != null ? ". " + event.getMessage() : ""));
    }

    @DltHandler
    public void dltHandler(@Payload Object failedEvent,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage,
                           @Header(KafkaHeaders.ORIGINAL_OFFSET) Long offset) {

        String payloadPreview = failedEvent instanceof byte[]
                ? new String((byte[]) failedEvent).substring(0, Math.min(100, ((byte[]) failedEvent).length)) + "..."
                : failedEvent.toString();

        log.error("Message failed and landed in DLT. Topic: {}, Offset: {}, Reason: {}, Payload preview: {}",
                topic, offset, exceptionMessage, payloadPreview);

    }
}