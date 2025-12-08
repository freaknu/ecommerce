package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventService {
    private final KafkaTemplate<String, OrderPlacedEvent>kafkaTemplate;
    public void placeOrderEvent(OrderPlacedEvent event) {
        log.info("before kafka call");
        kafkaTemplate.send("order_topic",event);
        log.info("message sent successfully");
    }
}
