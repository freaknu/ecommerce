package com.microservice.productservice.product_service.dto.discount;

import com.microservice.productservice.product_service.model.ProductModel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DiscountDto {
    private UUID id;
    private String couponName;
    private String couponDescription;
    private int discountPercentage;
    private String discountType;
    private String couponImage;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;

    private List<Long> allProductsId = new ArrayList<>();
}
