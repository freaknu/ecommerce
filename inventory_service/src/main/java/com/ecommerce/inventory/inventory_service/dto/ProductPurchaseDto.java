package com.ecommerce.inventory.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPurchaseDto {
    private Long productId;
    private Long categoryId;
    private Integer purchaseQuantity;
}
