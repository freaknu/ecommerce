package com.ecommerce.microservice.AuthService.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseFormat<T> {
    private T data;
    private String message;
    private boolean success;
    private Integer status;
    private LocalDateTime timestamp;
}