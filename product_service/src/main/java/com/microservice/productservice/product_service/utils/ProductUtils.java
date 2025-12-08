package com.microservice.productservice.product_service.utils;

import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.mapper.CategoryMapper;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.repository.CategoryRepository;
import com.microservice.productservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUtils {
    private final CategoryRepository categoryRepository;
//    private final ProductRepository productRepository;

    public List<CategoryModel> getCategories(List<Long> categoryId) {
        return categoryRepository.findAllById(categoryId);
    }
}
