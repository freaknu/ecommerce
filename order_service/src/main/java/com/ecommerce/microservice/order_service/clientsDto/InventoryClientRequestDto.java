package com.ecommerce.microservice.order_service.clientsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryClientRequestDto {
    private Long productId;
    private Long categoryId;
    private Integer purchaseQuantity;
}
