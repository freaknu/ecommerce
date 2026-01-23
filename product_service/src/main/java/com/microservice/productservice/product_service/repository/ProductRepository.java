package com.microservice.productservice.product_service.repository;

import com.microservice.productservice.product_service.model.ProductModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel,Long>,
        JpaSpecificationExecutor<ProductModel> {
    List<ProductModel>findAllByUserId(Long userId,Pageable pageable);
    List<ProductModel>findAllByCategoryId(Long categoryId, Pageable pageable);


    @Query("select distinct p.brandName from ProductModel p where p.brandName is not null")
    List<String> findDistinctBrands();

    @Query("select distinct p.fabric from ProductModel p where p.fabric is not null")
    List<String> findDistinctFabrics();

    @Query("select distinct p.fit from ProductModel p where p.fit is not null")
    List<String> findDistinctFits();

    @Query("select distinct p.sleeveType from ProductModel p where p.sleeveType is not null")
    List<String> findDistinctSleeveTypes();

    @Query("select distinct p.neckType from ProductModel p where p.neckType is not null")
    List<String> findDistinctNeckTypes();

    @Query("select distinct p.pattern from ProductModel p where p.pattern is not null")
    List<String> findDistinctPatterns();

    @Query("select distinct p.occasion from ProductModel p where p.occasion is not null")
    List<String> findDistinctOccasions();

    @Query("select distinct p.season from ProductModel p where p.season is not null")
    List<String> findDistinctSeasons();
}
