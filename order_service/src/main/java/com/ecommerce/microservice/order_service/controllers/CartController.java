package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.dto.CartDto;
import com.ecommerce.microservice.order_service.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cart Controller",description = "Cart Controller for Ecommerce")
public class CartController {
    private final CartService cartService;
    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        try {
            var res = cartService.getCartByUserId(userId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("the error at get cart {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/createCart/{userId}")
    public ResponseEntity<CartDto> addToCart(@RequestBody CartDto cartDto) {
        try {
            var res = cartService.addToCart(cartDto);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("the error at add to  cart {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/deleteFromCart/{userId}/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long userId,@PathVariable Long productId) {
        try {
            var res = cartService.deleteFromCart(productId,userId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("the error at delete cart {}", e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}
