package com.ecommerce.microservice.order_service.clientsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryClientResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
