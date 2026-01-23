package com.ecommerce.microservice.order_service.repository;

import com.ecommerce.microservice.order_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByBuyerId(Long buyerId);

    List<Payment> findByProductId(Long productId);

    List<Payment> findBySellerId(Long sellerId);
}
