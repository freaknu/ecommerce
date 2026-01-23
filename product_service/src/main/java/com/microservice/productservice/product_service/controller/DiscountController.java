package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.aop.RoleAnnotation;
import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.config.ContextFactory;
import com.microservice.productservice.product_service.dto.discount.DiscountDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.service.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Discount Controller", description = "Discount Controller for Ecommerce")
public class DiscountController {

    private final DiscountService discountService;
    private final ContextFactory contexts;
    private final Logger log = LoggerFactory.getLogger(DiscountController.class);

    @GetMapping("/getAllDiscounts")
    public ResponseEntity<ApiResponseFormat<List<DiscountDto>>> getAllDiscounts() {
        List<DiscountDto> res = discountService.getAll();
        contexts.getRoles();

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All discounts fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/createDiscount")
    public ResponseEntity<ApiResponseFormat<DiscountDto>> createDiscount(
            @RequestBody DiscountDto discountDto
    ) {
        DiscountDto res = discountService.createDiscount(discountDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Discount created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/updateDiscount/{id}")
    public ResponseEntity<ApiResponseFormat<DiscountDto>> updateDiscount(
            @RequestBody DiscountDto discountDto,
            @PathVariable UUID id
    ) {
        DiscountDto res = discountService.updateDiscount(discountDto, id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Discount updated successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_ADMIN")
    @DeleteMapping("/deleteDiscount/{id}")
    public ResponseEntity<ApiResponseFormat<Boolean>> deleteDiscount(
            @PathVariable UUID id
    ) throws Exception {
        Boolean res = discountService.deleteDiscount(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Discount deleted successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/findByDiscountId/{id}")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> getAllProductsByDiscount(
            @PathVariable UUID id
    ) throws Exception {
        List<ProductCreateResponseDto> res = discountService.getProductsByDiscountId(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by discountId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/findByProductId/{id}")
    public ResponseEntity<ApiResponseFormat<List<DiscountDto>>> getAllDiscountsByProduct(
            @PathVariable Long id
    ) throws Exception {
        List<DiscountDto> res = discountService.getAllDiscountsByProductId(id);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Discounts fetched by productId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
