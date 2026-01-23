package com.ecommerce.inventory.inventory_service.controller;

import com.ecommerce.inventory.inventory_service.common.ApiResponseFormat;
import com.ecommerce.inventory.inventory_service.common.InventoryException;
import com.ecommerce.inventory.inventory_service.dto.InventoryCreateDto;
import com.ecommerce.inventory.inventory_service.dto.ProductPurchaseDto;
import com.ecommerce.inventory.inventory_service.model.Inventory;
import com.ecommerce.inventory.inventory_service.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Tag(name = "Inventory Controller", description = "API for the Inventory Service")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/create-inventory")
    public ResponseEntity<ApiResponseFormat<Inventory>> addToInventory(
            @RequestBody InventoryCreateDto dto
    ) {
        Inventory res = inventoryService.createOrUpdateInventory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Inventory created/updated successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getByProductId/{id}")
    public ResponseEntity<ApiResponseFormat<Inventory>> getByProductId(
            @PathVariable Long id
    ) throws InventoryException {
        Inventory res = inventoryService.inventoryByProductId(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Inventory fetched by productId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getByCategory/{id}")
    public ResponseEntity<ApiResponseFormat<Inventory>> getByCategory(
            @PathVariable Long id
    ) throws InventoryException {
        Inventory res = inventoryService.inventoryByCategoryId(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Inventory fetched by categoryId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/placeOrderUtil")
    public ResponseEntity<ApiResponseFormat<Boolean>> placeOrder(
            @RequestBody ProductPurchaseDto data
    ) throws InventoryException {
        boolean res = inventoryService.orderProduct(data);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Order processed successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getAllInventory")
    public ResponseEntity<ApiResponseFormat<List<Inventory>>> getAllInventory() {
        List<Inventory> res = inventoryService.getAllInventory();

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All inventory fetched",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/deleteByProductId/{productId}")
    public ResponseEntity<ApiResponseFormat<Boolean>> deleteInventoryByProductId(
            @PathVariable Long productId
    ) {
        boolean res = inventoryService.deleteByProductId(productId);

        log.info("Deleted inventory for productId : {}", productId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Inventory deleted successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
