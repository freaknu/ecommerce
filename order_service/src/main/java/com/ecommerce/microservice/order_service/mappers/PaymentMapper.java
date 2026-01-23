package com.ecommerce.microservice.order_service.mappers;

import com.ecommerce.microservice.order_service.dto.PaymentDto;
import com.ecommerce.microservice.order_service.model.Payment;

import java.time.LocalDateTime;

public class PaymentMapper {
    public static Payment toPayment(PaymentDto dto) {
        return Payment
                .builder()
                .paymentModes(dto.getPaymentModes())
                .buyerId(dto.getBuyerId())
                .sellerId(dto.getSellerId())
                .productId(dto.getProductId())
                .build();
    }

    public static PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto
                .builder()
                .id(payment.getId())
                .isPaid(payment.getIsPaid())
                .paymentModes(payment.getPaymentModes())
                .sellerId(payment.getSellerId())
                .buyerId(payment.getBuyerId())
                .productId(payment.getProductId())
                .payDateTime(payment.getPayDateTime())
                .orderId(payment.getOrder().getId())
                .build();

    }
}
