package com.microservice.productservice.product_service.dto.filter;

import lombok.Data;
import java.util.List;

@Data
public class ProductFilterResponseDto {

    private List<String> categories;
    private List<String> genders;
    private List<String> brands;
    private List<String> sizes;
    private List<String> fabrics;
    private List<String> fits;
    private List<String> sleeveTypes;
    private List<String> neckTypes;
    private List<String> patterns;
    private List<String> occasions;
    private List<String> seasons;
    private Integer minPrice;
    private Integer maxPrice;
}
