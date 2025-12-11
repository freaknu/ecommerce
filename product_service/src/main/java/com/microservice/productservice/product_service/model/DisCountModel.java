package com.microservice.productservice.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "discount_model")
public class DisCountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String couponName;
    private String couponDescription;
    private int discountPercentage;
    private String discountType;
    private String couponImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime validFrom;
    private LocalDateTime validTill;
    private boolean isActive;
    @ManyToMany(mappedBy = "allDiscounts")
    private List<ProductModel> allProducts = new ArrayList<>();

}
