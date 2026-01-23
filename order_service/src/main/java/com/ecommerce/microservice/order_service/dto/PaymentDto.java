package com.ecommerce.microservice.order_service.dto;


import com.ecommerce.microservice.order_service.enums.PaymentModes;
import com.ecommerce.microservice.order_service.model.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentDto {
    private UUID id;

    private Long orderId;
    private Long productId;
    private Long buyerId;
    private Long sellerId;
    private Boolean isPaid;
    private LocalDateTime payDateTime;
    private PaymentModes paymentModes;
}
