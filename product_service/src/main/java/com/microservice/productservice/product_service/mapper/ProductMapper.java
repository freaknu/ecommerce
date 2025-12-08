package com.microservice.productservice.product_service.mapper;

import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreaterequestDto;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.model.ProductModel;

public class ProductMapper {
    public static ProductModel toProductModel(ProductCreaterequestDto data, CategoryModel category) {
        ProductModel product = new ProductModel();
        product.setProductName(data.getProductName());
        product.setProductDescription(data.getProductDescription());
        product.setProductPrice(data.getProductPrice());
        product.setProductsImages(data.getProductsImages());
        product.setUserId(data.getUserId());
        product.setSizes(data.getSize());
        product.setCategory(category);
        return product;
    }
    // ProductMapper.java
    public static ProductCreateResponseDto toProductCreateResponseDto(ProductModel product) {
        ProductCreateResponseDto dto = new ProductCreateResponseDto();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductsImages(product.getProductsImages());

        // FIXED: Null-safe category name
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null);

        return dto;
    }
}
