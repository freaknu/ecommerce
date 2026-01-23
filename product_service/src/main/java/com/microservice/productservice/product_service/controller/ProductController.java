package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.aop.RoleAnnotation;
import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.dto.filter.ProductFilterResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreaterequestDto;
import com.microservice.productservice.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product Controller", description = "Controller for Product")
public class ProductController {

    private final ProductService productService;
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/getallProducts")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ProductCreateResponseDto> res = productService.getAllProducts(page, size);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All products fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/createProduct/{quantity}")
    public ResponseEntity<ApiResponseFormat<ProductCreateResponseDto>> createProduct(
            @RequestBody ProductCreaterequestDto data,
            @PathVariable int quantity
    ) {
        ProductCreateResponseDto res = productService.createProduct(data, quantity);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Product created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ApiResponseFormat<ProductCreateResponseDto>> getProductById(
            @PathVariable Long id
    ) {
        ProductCreateResponseDto res = productService.getById(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Product fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<ApiResponseFormat<ProductCreateResponseDto>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductCreaterequestDto data
    ) {
        ProductCreateResponseDto res = productService.updateProduct(id, data);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Product updated successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_ADMIN")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<ApiResponseFormat<Boolean>> deleteProduct(
            @PathVariable Long id
    ) {
        productService.deleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        true,
                        "Product deleted successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getallProductsByCategory/{id}")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getAllProductsByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ProductCreateResponseDto> res =
                productService.getAllProductsByCategoryId(id, page, size);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by category",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getAllProductsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ProductCreateResponseDto> res =
                productService.getAllProductsByUserId(userId, page, size);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by userId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/getAllProductsByIds")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getAllProductsByProductIds(
            @RequestBody List<Long> ids
    ) {
        List<ProductCreateResponseDto> res =
                productService.getAllProductsByProductIds(ids);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by ids",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/getProductByFilter")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getFilteredProduct(
            @RequestBody ProductFilterResponseDto dto
    ) {
        List<ProductCreateResponseDto> res = productService.filter(dto);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by filter",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/getProductsByQuery")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getProductsByQuery(
            @RequestBody String query
    ) {
        List<ProductCreateResponseDto> res =
                productService.getAllProductsByQuery(query);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by query",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
