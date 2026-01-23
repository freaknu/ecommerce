package com.microservice.productservice.product_service.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String userName;
    private String description;
    private int rating;
    private LocalDateTime createdAt;
}
