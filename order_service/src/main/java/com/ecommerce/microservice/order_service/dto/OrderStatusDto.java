package com.ecommerce.microservice.order_service.dto;

import com.ecommerce.microservice.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderStatusDto {
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
