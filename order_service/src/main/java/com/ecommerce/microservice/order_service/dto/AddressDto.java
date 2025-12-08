package com.ecommerce.microservice.order_service.dto;

import com.ecommerce.microservice.order_service.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
        private Long id;
        private Long userId;
        private AddressType addressType = AddressType.HOME;
        private String name;
        private String email;
        private String address;
        private String city;
        private String state;
        private String landmark;
        private String pinCode;
        private String phoneNumber;
        private double latitude;
        private double longitude;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
}
