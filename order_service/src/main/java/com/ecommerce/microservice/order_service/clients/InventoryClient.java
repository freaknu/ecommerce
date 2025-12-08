package com.ecommerce.microservice.order_service.clients;

import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientResponse;
import org.springframework.web.bind.annotation.RequestBody;   // ✔ correct import
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryClient {

    @PostExchange("/api/inventory/placeOrderUtil")
    InventoryClientResponse placeOrder(@RequestBody InventoryClientRequestDto inventoryClientRequestDto);
}
