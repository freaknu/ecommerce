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

    private String fabric;        // Cotton, Denim, Linen
    private String fit;           // Slim, Regular, Oversized
    private String sleeveType;    // Full, Half, Sleeveless
    private String neckType;      // Round, V-Neck, Polo
    private String pattern;       // Solid, Printed, Checked
    private String occasion;      // Casual, Formal, Party
    private String season;        // Summer, Winter, All



    private String brandName;
    private List<String> genders = new ArrayList<>();
}

