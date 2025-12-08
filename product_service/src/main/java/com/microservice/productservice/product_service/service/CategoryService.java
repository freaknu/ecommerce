package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.common.ResourceNotFoundException;
import com.microservice.productservice.product_service.dto.category.CategoryCreateRequestDto;
import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.mapper.CategoryMapper;
import com.microservice.productservice.product_service.mapper.ProductMapper;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(categoryCreateRequestDto.getCategoryName());
        categoryModel.setCategoryDescription(categoryCreateRequestDto.getCategoryDescription());
        categoryModel.setCategoryImage(categoryCreateRequestDto.getCategoryImage());
        categoryModel.setUserId(categoryCreateRequestDto.getUserId());
        CategoryModel savedCategory = categoryRepository.save(categoryModel);
        return CategoryMapper.toCategoryCreateResponseDto(savedCategory);
    }

    public List<CategoryCreateResponseDto> getAllCategory() {
        List<CategoryModel> allCategory = categoryRepository.findAll();
        return allCategory.stream().map(CategoryMapper::toCategoryCreateResponseDto).toList();
    }
    public CategoryCreateResponseDto getCategoryById(Long id) {
        CategoryModel categoryModel = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id :" + id));
        return CategoryMapper.toCategoryCreateResponseDto(categoryModel);
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }


    @Transactional
    public CategoryCreateResponseDto updateCategory(Long id,CategoryCreateRequestDto categoryCreateRequestDto) {
        CategoryModel categoryModel = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found" + id));
        categoryModel.setCategoryName(categoryCreateRequestDto.getCategoryName());
        categoryModel.setCategoryDescription(categoryCreateRequestDto.getCategoryDescription());
        categoryModel.setCategoryImage(categoryCreateRequestDto.getCategoryImage());
        CategoryModel savedCategory = categoryRepository.save(categoryModel);
        return  CategoryMapper.toCategoryCreateResponseDto(savedCategory);
    }

    public CategoryModel findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id: " + id));
    }

    public List<CategoryCreateResponseDto> getAllCategoryUserId(Long userId) {
        return categoryRepository.findAllByUserId(userId)
                .stream()
                .map(CategoryMapper::toCategoryCreateResponseDto).toList();
    }
}
