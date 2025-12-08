package com.ecommerce.inventory.inventory_service.service;

import com.ecommerce.inventory.inventory_service.common.InventoryException;
import com.ecommerce.inventory.inventory_service.dto.ErrorResponse;
import com.ecommerce.inventory.inventory_service.dto.InventoryCreateDto;
import com.ecommerce.inventory.inventory_service.dto.ProductPurchaseDto;
import com.ecommerce.inventory.inventory_service.enums.StockStatus;
import com.ecommerce.inventory.inventory_service.model.Inventory;
import com.ecommerce.inventory.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory createOrUpdateInventory(InventoryCreateDto dto) {

        Inventory inventory = inventoryRepository
                .findByProductIdAndCategoryId(dto.productId(), dto.categoryId())
                .orElse(null);

        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProductId(dto.productId());
            inventory.setCategoryId(dto.categoryId());
            inventory.setQuantity(dto.quantity());
            inventory.setAvailableQuantity(dto.quantity());
            inventory.setReservedQuantity(0);
            inventory.setCreatedAt(LocalDateTime.now());
        } else {
            inventory.setQuantity(inventory.getQuantity() + dto.quantity());
            inventory.setAvailableQuantity(inventory.getAvailableQuantity() + dto.quantity());
            inventory.setUpdatedAt(LocalDateTime.now());
        }

        return inventoryRepository.save(inventory);
    }

    public Inventory inventoryByProductId(Long productId) throws InventoryException {
        return inventoryRepository
                .findTopByProductId(productId)
                .orElseThrow(() ->
                        new InventoryException("Inventory Not Found for Product Id : " + productId)
                );
    }

    public Inventory inventoryByCategoryId(Long categoryId) throws InventoryException {
        return inventoryRepository
                .findTopByCategoryId(categoryId)
                .orElseThrow(() ->
                        new InventoryException("Inventory Not Found for Category Id : " + categoryId)
                );
    }

    @Transactional
    public ErrorResponse orderProduct(ProductPurchaseDto data) throws InventoryException {

        Inventory inventory = inventoryRepository
                .findByProductIdAndCategoryIdForUpdate(
                        data.getProductId(),
                        data.getCategoryId()
                )
                .orElseThrow(() -> new InventoryException("Product not found"));

        if (inventory.getAvailableQuantity() < data.getPurchaseQuantity()) {
            throw new InventoryException("OUT_OF_STOCK");
        }

        int qty = data.getPurchaseQuantity();

        inventory.setQuantity(inventory.getQuantity() - qty);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - qty);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);

        return new ErrorResponse(200, "Purchase successful", LocalDateTime.now());
    }
}
