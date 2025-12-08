package com.ecommerce.microservice.order_service.model;

import com.ecommerce.microservice.order_service.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AddressType addressType = AddressType.HOME;
    private String name;
    private String email;
    private String address;
    private String city;
    private String state;
    private String landmark;

    @Column(length = 6)
    private String pinCode;

    private String phoneNumber;

    private double latitude;
    private double longitude;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
