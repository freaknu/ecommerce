package com.microservice.productservice.product_service.clients.dto;

import java.util.List;

public record InventoryCreateDto(

        Long productId,
        Long categoryId,
        Integer quantity
) {
}
