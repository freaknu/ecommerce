package com.ecommerce.inventory.inventory_service.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;

import java.time.LocalDateTime;
import java.util.List;

public class InventoryResponseDto
{
    private Long id;
    private Long productId;

    private List<Long> categoryId;
    private Integer quantity;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
