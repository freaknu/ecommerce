package com.microservice.productservice.product_service.clients.interfaces;

import com.microservice.productservice.product_service.clients.dto.InventoryCreateDto;
import com.microservice.productservice.product_service.clients.dto.InventoryResponse;
import com.microservice.productservice.product_service.common.ApiResponseFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryClient {
    @PostExchange("api/inventory/create-inventory")
    ApiResponseFormat<InventoryResponse> createInventory(@RequestBody InventoryCreateDto dto);

    @DeleteExchange("api/inventory/deleteByProductId/{productId}")
    Boolean deleteInventoryByProductId(@PathVariable Long productId);
}
