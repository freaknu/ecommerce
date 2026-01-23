package com.microservice.productservice.product_service.dto.product;

import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long createdById;
    private String productName;
    private String productDescription;
    private int productPrice;
    private List<String> productsImages = new ArrayList<>();
    private List<String> sizes = new ArrayList<>();
    private String categoryName;
    private Long categoryId;

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
