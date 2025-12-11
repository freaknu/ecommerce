package com.microservice.productservice.product_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductCreaterequestDto {
    private Long userId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private List<String> productsImages = new ArrayList<>();
    private Long categoryId;
    private List<String> size = new ArrayList<>();
    private String createdBy;
}

