package com.microservice.productservice.product_service.clients.interfaces;

import com.microservice.productservice.product_service.clients.dto.InventoryCreateDto;
import com.microservice.productservice.product_service.clients.dto.InventoryResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryClient {
    @PostExchange("api/inventory/create-inventory")
    InventoryResponse createInventory(@RequestBody InventoryCreateDto dto);
}
