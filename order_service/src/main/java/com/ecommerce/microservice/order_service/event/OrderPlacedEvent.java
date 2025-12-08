package com.ecommerce.microservice.order_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
}
