package com.microservice.productservice.product_service.mapper;

import com.microservice.productservice.product_service.dto.category.CategoryCreateRequestDto;
import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.model.CategoryModel;

public class CategoryMapper {
    public static CategoryModel toCategoryModel(CategoryCreateRequestDto category) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(category.getCategoryName());
        categoryModel.setCategoryDescription(category.getCategoryDescription());
        categoryModel.setCategoryImage(category.getCategoryImage());
        categoryModel.setUserId(category.getUserId());
        return categoryModel;
    }

    public static CategoryCreateResponseDto toCategoryCreateResponseDto(CategoryModel category) {
        CategoryCreateResponseDto categoryCreateResponseDto = new CategoryCreateResponseDto();
        categoryCreateResponseDto.setCategoryName(category.getCategoryName());
        categoryCreateResponseDto.setCategoryDescription(category.getCategoryDescription());
        categoryCreateResponseDto.setCategoryImage(category.getCategoryImage());
        categoryCreateResponseDto.setCategoryId(category.getId());
        return categoryCreateResponseDto;
    }
}
