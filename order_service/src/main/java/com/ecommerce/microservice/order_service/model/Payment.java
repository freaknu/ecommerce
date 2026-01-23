package com.ecommerce.microservice.order_service.model;

import com.ecommerce.microservice.order_service.enums.PaymentModes;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "payments")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long productId;
    private Long buyerId;
    private Long sellerId;
    private Boolean isPaid = false;

    @CreatedDate
    private LocalDateTime payDateTime;
    @OneToOne(mappedBy = "payment")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentModes paymentModes = PaymentModes.UPI;
}
