package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import com.ecommerce.microservice.order_service.dto.PaymentDetailsDto;
import com.ecommerce.microservice.order_service.service.PaymentDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Tag(name = "PaymentDetails Controller", description = "Payment details Controller for Orders")
@RequestMapping("/api/order")
@Slf4j
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @GetMapping("/getPaymentDetail")
    public ResponseEntity<ApiResponseFormat<PaymentDetailsDto>> getPaymentDetails(
            @RequestParam Long sellerId
    ) throws Exception {
        PaymentDetailsDto res = paymentDetailsService.getPaymentDetail(sellerId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Payment details fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/addPaymentDetails")
    public ResponseEntity<ApiResponseFormat<PaymentDetailsDto>> addPaymentDetails(
            @RequestBody PaymentDetailsDto paymentDetailsDto
    ) {
        PaymentDetailsDto res = paymentDetailsService.addOrModifyPaymentDetails(paymentDetailsDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Payment details saved successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }
}
