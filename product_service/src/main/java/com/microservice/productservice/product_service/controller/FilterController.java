package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.dto.filter.ProductFilterResponseDto;
import com.microservice.productservice.product_service.service.FilterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "FilterController", description = "Filter Controller for E commerce")
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/getAllFilters")
    public ResponseEntity<ApiResponseFormat<ProductFilterResponseDto>> getAllFilters() {
        ProductFilterResponseDto res = filterService.getFilters();

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All filters fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
