package com.ecommerce.inventory.inventory_service.common;

import com.ecommerce.inventory.inventory_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseFormat<Object>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponseFormat<>(
                        null,
                        ex.getMessage(),
                        false,
                        500,
                        LocalDateTime.now()
                )
        );
    }
}
