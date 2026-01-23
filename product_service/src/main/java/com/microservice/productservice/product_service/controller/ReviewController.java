package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.dto.review.ReviewDto;
import com.microservice.productservice.product_service.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Review Controller", description = "Review Controller for E commerce")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/getAllReviews/{productId}")
    public ResponseEntity<ApiResponseFormat<List<ReviewDto>>> getAllReview(
            @PathVariable Long productId
    ) {
        List<ReviewDto> res = reviewService.getAllReviewsByProductId(productId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Reviews fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/createReview/{productId}")
    public ResponseEntity<ApiResponseFormat<ReviewDto>> createReviews(
            @PathVariable Long productId,
            @RequestBody ReviewDto data
    ) throws Exception {
        ReviewDto res = reviewService.createReview(productId, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Review created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }
}
