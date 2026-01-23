package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import com.ecommerce.microservice.order_service.dto.PaymentDto;
import com.ecommerce.microservice.order_service.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Payment Controller", description = "Payment Controller for Orders")
@Slf4j
public class PaymentController {

    private final PaymentService paymentDetailsService;

    @PostMapping("/pay")
    public ResponseEntity<ApiResponseFormat<PaymentDto>> pay(
            @RequestBody PaymentDto dto
    ) throws Exception {
        PaymentDto res = paymentDetailsService.pay(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Payment successful",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getPaymentByProductId/{productId}")
    public ResponseEntity<ApiResponseFormat<List<PaymentDto>>> getPaymentsByProductId(
            @PathVariable Long productId
    ) {
        List<PaymentDto> res = paymentDetailsService.getPaymentByProductId(productId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Payments fetched by productId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getPaymentBySellerId/{sellerId}")
    public ResponseEntity<ApiResponseFormat<List<PaymentDto>>> getPaymentBySellerId(
            @PathVariable Long sellerId
    ) {
        List<PaymentDto> res = paymentDetailsService.getPaymentBySellerId(sellerId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Payments fetched by sellerId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getPaymentByBuyerId/{buyerId}")
    public ResponseEntity<ApiResponseFormat<List<PaymentDto>>> getPaymentByBuyerId(
            @PathVariable Long buyerId
    ) {
        List<PaymentDto> res = paymentDetailsService.getPaymentByUserId(buyerId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Payments fetched by buyerId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getPaymentByOrderId/{orderId}")
    public ResponseEntity<ApiResponseFormat<PaymentDto>> getByOrderId(
            @PathVariable Long orderId
    ) {
        PaymentDto res = paymentDetailsService.getPaymentByOrderId(orderId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Payment fetched by orderId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
