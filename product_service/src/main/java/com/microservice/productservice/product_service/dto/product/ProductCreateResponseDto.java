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
    private String productName;
    private String productDescription;
    private int productPrice;
    private List<String> productsImages = new ArrayList<>();
    private String categoryName;
}
