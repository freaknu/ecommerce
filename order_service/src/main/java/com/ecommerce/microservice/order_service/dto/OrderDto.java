package com.ecommerce.microservice.order_service.dto;

import com.ecommerce.microservice.order_service.enums.OrderStatus;
import com.ecommerce.microservice.order_service.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Address address;
    private LocalDateTime orderDate;
    private LocalDateTime orderAt;
    private LocalDateTime deliveryDate;
    private OrderStatus orderStatus;
}
