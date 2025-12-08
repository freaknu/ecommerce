package com.microservice.productservice.product_service.repository;

import com.microservice.productservice.product_service.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel,Long> {
    List<CategoryModel>findAllByUserId(Long userId);
}
