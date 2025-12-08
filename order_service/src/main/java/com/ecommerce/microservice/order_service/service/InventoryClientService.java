package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.clients.InventoryClient;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryClientService {
    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryCB",fallbackMethod = "inventoryFallback")
    public InventoryClientResponse placedOrder(InventoryClientRequestDto dto) {
        return inventoryClient.placeOrder(dto);
    }

    public InventoryClientResponse inventoryFallback(InventoryClientRequestDto dto){
        return new InventoryClientResponse();
    }
}
