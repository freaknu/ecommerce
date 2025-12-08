package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.clients.dto.InventoryCreateDto;
import com.microservice.productservice.product_service.clients.dto.InventoryResponse;
import com.microservice.productservice.product_service.clients.interfaces.InventoryClient;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.model.ProductModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryClientService {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryCB",fallbackMethod = "inventoryFallback")
    public InventoryResponse createOrUpdateInventory(ProductModel product, Long categoryId, int quantity) {
        InventoryCreateDto inventoryDto = new InventoryCreateDto(
                product.getId(),
                categoryId,
                quantity
        );
        return inventoryClient.createInventory(inventoryDto);
    }

    public InventoryResponse inventoryFallback(ProductModel product, Long categoryId, int quantity, Throwable t) {
        return new InventoryResponse();
    }

}
