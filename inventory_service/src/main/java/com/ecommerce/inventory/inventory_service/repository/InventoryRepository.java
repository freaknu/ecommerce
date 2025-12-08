package com.ecommerce.inventory.inventory_service.repository;

import com.ecommerce.inventory.inventory_service.model.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductIdAndCategoryId(Long productId, Long categoryId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.productId = :productId AND i.categoryId = :categoryId")
    Optional<Inventory> findByProductIdAndCategoryIdForUpdate(Long productId, Long categoryId);

    Optional<Inventory> findTopByProductId(Long productId);

    Optional<Inventory> findTopByCategoryId(Long categoryId);
}
