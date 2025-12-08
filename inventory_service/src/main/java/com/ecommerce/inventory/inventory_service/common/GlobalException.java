package com.ecommerce.inventory.inventory_service.common;

import com.ecommerce.inventory.inventory_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<ErrorResponse> handleInventoryException( InventoryException ex) {
        ErrorResponse res = new ErrorResponse();
        res.setStatus(400);
        res.setMessage(ex.getMessage());
        res.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
