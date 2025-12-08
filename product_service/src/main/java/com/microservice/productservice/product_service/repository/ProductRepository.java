package com.microservice.productservice.product_service.repository;

import com.microservice.productservice.product_service.model.ProductModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel,Long> {
    List<ProductModel>findAllByUserId(Long userId,Pageable pageable);
    List<ProductModel>findAllByCategoryId(Long categoryId, Pageable pageable);
}
