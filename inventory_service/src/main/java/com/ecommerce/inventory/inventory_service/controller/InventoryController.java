package com.ecommerce.inventory.inventory_service.controller;

import com.ecommerce.inventory.inventory_service.dto.ErrorResponse;
import com.ecommerce.inventory.inventory_service.dto.InventoryCreateDto;
import com.ecommerce.inventory.inventory_service.dto.ProductPurchaseDto;
import com.ecommerce.inventory.inventory_service.model.Inventory;
import com.ecommerce.inventory.inventory_service.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Tag(name = "Inventory Controller",description = "Api for the Inventory Service")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/create-inventory")
    public ResponseEntity<Inventory> addToInventory( @RequestBody InventoryCreateDto dto) {
        try {
          var res = inventoryService.createOrUpdateInventory(dto);
          return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getByProductId/{id}")
    public ResponseEntity<Inventory> getByProductId(@PathVariable Long id) {
        try {
            var res = inventoryService.inventoryByProductId(id);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getByCategory/{id}")
    public ResponseEntity<Inventory> getByCategory(@PathVariable Long id) {
        try {
            var res = inventoryService.inventoryByCategoryId(id);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/placeOrderUtil")
    public ResponseEntity<ErrorResponse> placeOrder(@RequestBody ProductPurchaseDto data) {
        try {
            var res = inventoryService.orderProduct(data);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
