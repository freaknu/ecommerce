package com.microservice.productservice.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "product")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String productName;

    @Column(length = 1000)
    private String productDescription;
    private int productPrice;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private List<String> productsImages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;

    @ElementCollection
    @CollectionTable(name = "genders", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "gender")
    private List<String> genders = new ArrayList<>();

    private String brandName;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<String> sizes = new ArrayList<>();

    private String fabric;        // Cotton, Denim, Linen
    private String fit;           // Slim, Regular, Oversized
    private String sleeveType;    // Full, Half, Sleeveless
    private String neckType;      // Round, V-Neck, Polo
    private String pattern;       // Solid, Printed, Checked
    private String occasion;      // Casual, Formal, Party
    private String season;        // Summer, Winter, All


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "product_discount",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private List<DisCountModel> allDiscounts = new ArrayList<>();

    private String createdBy;
    private String personEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private List<ReviewModel>allReviews = new ArrayList<>();
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
