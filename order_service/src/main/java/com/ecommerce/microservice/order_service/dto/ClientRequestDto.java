package com.ecommerce.microservice.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientRequestDto {
    private Long productId;
    private Long categoryId;
    private Integer purchaseQuantity;
    private Long userId;
    private Long addressId;
    private Double discount;
}
