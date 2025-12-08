package com.microservice.productservice.product_service.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
@ControllerAdvice
public class CustomException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResoureNotFound(ResourceNotFoundException ex) {
        Map<String,Object>res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("message",ex.getMessage());
        res.put("status", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
    }
}
