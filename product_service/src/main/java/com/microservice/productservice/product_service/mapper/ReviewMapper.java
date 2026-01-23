package com.microservice.productservice.product_service.mapper;

import com.microservice.productservice.product_service.dto.review.ReviewDto;
import com.microservice.productservice.product_service.model.ReviewModel;

import java.time.LocalDateTime;

public class ReviewMapper {
    public static ReviewModel toReviewModel(ReviewDto reviewDto) {
        ReviewModel res = new ReviewModel();
        res.setDescription(reviewDto.getDescription());
        res.setRating(reviewDto.getRating());
        res.setCreatedAt(LocalDateTime.now());
        res.setUserName(reviewDto.getUserName());

        return res;
    }

    public static ReviewDto toReviewDto(ReviewModel reviewModel) {
        return ReviewDto
                .builder()
                .id(reviewModel.getId())
                .description(reviewModel.getDescription())
                .createdAt(reviewModel.getCreatedAt())
                .rating(reviewModel.getRating())
                .userName(reviewModel.getUserName())
                .build();
    }
}
