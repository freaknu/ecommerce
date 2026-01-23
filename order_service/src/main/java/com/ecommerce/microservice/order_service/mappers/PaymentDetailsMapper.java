package com.ecommerce.microservice.order_service.mappers;

import com.ecommerce.microservice.order_service.dto.PaymentDetailsDto;
import com.ecommerce.microservice.order_service.dto.PaymentDto;
import com.ecommerce.microservice.order_service.model.PaymentDetails;

public class PaymentDetailsMapper {
    public static PaymentDetails toPaymentDetails(PaymentDetailsDto dto) {
        return PaymentDetails
                .builder()
                .paymentModes(dto.getPaymentModes())
                .sellerQrCodeUrl(dto.getSellerQrCodeUrl())
                .sellerUpi(dto.getSellerUpi())
                .paymentModes(dto.getPaymentModes())
                .sellerId(dto.getSellerId())
                .build();
    }

    public static PaymentDetailsDto toPaymentDetailsDto(PaymentDetails paymentDetails) {
        return PaymentDetailsDto
                .builder()
                .id(paymentDetails.getId())
                .createdAt(paymentDetails.getCreatedAt())
                .sellerQrCodeUrl(paymentDetails.getSellerQrCodeUrl())
                .sellerUpi(paymentDetails.getSellerUpi())
                .paymentModes(paymentDetails.getPaymentModes())
                .sellerId(paymentDetails.getSellerId())
                .build();
    }
}
