package com.ecommerce.microservice.order_service.dto;

import com.ecommerce.microservice.order_service.enums.PaymentModes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class PaymentDetailsDto {
    private UUID id;
    private String sellerQrCodeUrl;
    private String sellerUpi;
    private Long sellerId;
    private PaymentModes paymentModes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
