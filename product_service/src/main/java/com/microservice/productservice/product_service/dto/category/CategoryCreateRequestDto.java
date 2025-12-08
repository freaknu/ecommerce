package com.microservice.productservice.product_service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateRequestDto {
    private Long userId;
    private String categoryName;
    private String categoryDescription;
    private String categoryImage;
}
