package com.microservice.productservice.product_service.mapper;

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
        product.setCreatedBy(data.getCreatedBy());

        product.setFit(data.getFit());
        product.setFabric(data.getFabric());
        product.setBrandName(data.getBrandName());
        product.setGenders(data.getGenders());
        product.setNeckType(data.getNeckType());
        product.setPattern(data.getPattern());
        product.setOccasion(data.getOccasion());
        product.setSeason(data.getSeason());
        product.setSleeveType(data.getSleeveType());
        return product;
    }

    public static ProductCreateResponseDto toProductCreateResponseDto(ProductModel product) {
        ProductCreateResponseDto dto = new ProductCreateResponseDto();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductsImages(product.getProductsImages());
        dto.setCreatedById(product.getUserId());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null);
        dto.setSizes(product.getSizes());

        dto.setFit(product.getFit());
        dto.setFabric(product.getFabric());
        dto.setBrandName(product.getBrandName());
        dto.setGenders(product.getGenders());
        dto.setNeckType(product.getNeckType());
        dto.setPattern(product.getPattern());
        dto.setOccasion(product.getOccasion());
        dto.setSeason(product.getSeason());
        dto.setSleeveType(product.getSleeveType());
        return dto;
    }
}
