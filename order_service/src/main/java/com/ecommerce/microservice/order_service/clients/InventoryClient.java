package com.ecommerce.microservice.order_service.clients;

import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientResponse;
import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryClient {

    @PostExchange("/api/inventory/placeOrderUtil")
    ApiResponseFormat<InventoryClientResponse> placeOrder(@RequestBody InventoryClientRequestDto inventoryClientRequestDto);
}
