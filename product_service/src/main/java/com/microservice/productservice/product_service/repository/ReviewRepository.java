package com.microservice.productservice.product_service.repository;

import com.microservice.productservice.product_service.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel,Long> {
    List<ReviewModel> findAllByProductId(Long productId);
}
