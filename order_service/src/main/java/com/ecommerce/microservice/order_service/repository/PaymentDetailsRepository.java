package com.ecommerce.microservice.order_service.repository;

import com.ecommerce.microservice.order_service.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
    Optional<PaymentDetails> findBySellerId(Long sellerId);
}
