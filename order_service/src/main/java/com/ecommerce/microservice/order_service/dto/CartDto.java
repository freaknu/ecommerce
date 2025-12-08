package com.ecommerce.microservice.order_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CartDto {
    private Long id;
    private Long userId;
    private List<Long> productIds;
}
