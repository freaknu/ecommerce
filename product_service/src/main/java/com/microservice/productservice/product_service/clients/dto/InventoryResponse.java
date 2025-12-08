package com.microservice.productservice.product_service.clients.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InventoryResponse {
    private Long id;
    private Long productId;
    private Long categoryId;
    private Integer quantity;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
