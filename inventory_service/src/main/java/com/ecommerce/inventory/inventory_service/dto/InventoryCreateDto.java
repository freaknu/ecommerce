package com.ecommerce.inventory.inventory_service.dto;

public record InventoryCreateDto(
        Long productId,
        Long categoryId,
        Integer quantity
) {}
