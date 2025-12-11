package com.microservice.productservice.product_service.repository;

import com.microservice.productservice.product_service.model.DisCountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<DisCountModel, UUID> {
}
