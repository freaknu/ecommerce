package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import com.ecommerce.microservice.order_service.dto.CartDto;
import com.ecommerce.microservice.order_service.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cart Controller", description = "Cart Controller for Ecommerce")
public class CartController {

    private final CartService cartService;

    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<ApiResponseFormat<CartDto>> getCart(
            @PathVariable Long userId
    ) throws Exception {
        CartDto res = cartService.getCartByUserId(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Cart fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/createCart/{userId}")
    public ResponseEntity<ApiResponseFormat<CartDto>> addToCart(
            @RequestBody CartDto cartDto
    ) {
        CartDto res = cartService.addToCart(cartDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Product added to cart",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/deleteFromCart/{userId}/{productId}")
    public ResponseEntity<ApiResponseFormat<CartDto>> removeProductFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) throws Exception {
        CartDto res = cartService.deleteFromCart(productId, userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Product removed from cart",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
