package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.dto.category.CategoryCreateRequestDto;
import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Category Controller", description = "Controllers for Category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getallCategory")
    public ResponseEntity<ApiResponseFormat<List<CategoryCreateResponseDto>>> getAllCategories() {
        List<CategoryCreateResponseDto> res = categoryService.getAllCategory();

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All categories fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponseFormat<CategoryCreateResponseDto>> createCategory(
            @RequestBody CategoryCreateRequestDto data
    ) {
        log.info("the incoming user id is :{}", data.getUserId());

        CategoryCreateResponseDto res = categoryService.createCategory(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Category created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<ApiResponseFormat<CategoryCreateResponseDto>> getCategoryById(
            @PathVariable Long id
    ) {
        CategoryCreateResponseDto res = categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Category fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/updateCategoryById/{id}")
    public ResponseEntity<ApiResponseFormat<Boolean>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryCreateRequestDto data
    ) {
        categoryService.updateCategory(id, data);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        true,
                        "Category updated successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ApiResponseFormat<Boolean>> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        true,
                        "Category deleted successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getAllCategoryByUserId/{userId}")
    public ResponseEntity<ApiResponseFormat<List<CategoryCreateResponseDto>>> getAllCategoryUserId(
            @PathVariable Long userId
    ) {
        List<CategoryCreateResponseDto> res = categoryService.getAllCategoryUserId(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Categories fetched by userId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
