package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.dto.filter.ProductFilterResponseDto;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.repository.CategoryRepository;
import com.microservice.productservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductFilterResponseDto getFilters() {
        ProductFilterResponseDto r = new ProductFilterResponseDto();

        r.setCategories(categoryRepository.findAll()
                .stream().map(CategoryModel::getCategoryName).toList());

        r.setBrands(productRepository.findDistinctBrands());
        r.setFabrics(productRepository.findDistinctFabrics());
        r.setFits(productRepository.findDistinctFits());
        r.setSleeveTypes(productRepository.findDistinctSleeveTypes());
        r.setNeckTypes(productRepository.findDistinctNeckTypes());
        r.setPatterns(productRepository.findDistinctPatterns());
        r.setOccasions(productRepository.findDistinctOccasions());
        r.setSeasons(productRepository.findDistinctSeasons());

        return r;
    }
}
