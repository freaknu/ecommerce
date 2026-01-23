package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import com.ecommerce.microservice.order_service.aop.RoleAnnotation;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.dto.ClientRequestDto;
import com.ecommerce.microservice.order_service.dto.OrderDto;
import com.ecommerce.microservice.order_service.dto.OrderStatusDto;
import com.ecommerce.microservice.order_service.service.OrderService;
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
@Slf4j
@Tag(name = "Order Controller", description = "Order Controller for Ecommerce")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getAllOrders/{userId}")
    public ResponseEntity<ApiResponseFormat<List<OrderDto>>> getAllOrdersByUserId(
            @PathVariable Long userId
    ) {
        List<OrderDto> res = orderService.getAllOrdersbyUserId(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Orders fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    // @RoleAnnotation("ROLE_USER")
    @PostMapping("/placeOrder/{adminUserId}")
    public ResponseEntity<ApiResponseFormat<OrderDto>> placeOrderByUser(
            @RequestBody ClientRequestDto dto,
            @PathVariable Long adminUserId
    ) throws Exception {
        InventoryClientRequestDto request = new InventoryClientRequestDto();
        request.setCategoryId(dto.getCategoryId());
        request.setProductId(dto.getProductId());
        request.setPurchaseQuantity(dto.getPurchaseQuantity());

        OrderDto res = orderService.placeOrder(
                request,
                dto.getAddressId(),
                dto.getUserId(),
                dto.getDiscount(),
                adminUserId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Order placed successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @RoleAnnotation("ROLE_USER")
    @PostMapping("/cancelOrder/{orderId}")
    public ResponseEntity<ApiResponseFormat<OrderDto>> cancelOrderByOrderId(
            @PathVariable Long orderId
    ) throws Exception {
        OrderDto res = orderService.cancelOrder(orderId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Order cancelled successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getOrdersByProductId/{productId}")
    public ResponseEntity<ApiResponseFormat<List<OrderDto>>> getAllOrders(
            @PathVariable Long productId
    ) {
        List<OrderDto> res = orderService.getAllOrderByProductId(productId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Orders fetched by productId",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/changeOrderStatus/{orderId}")
    public ResponseEntity<ApiResponseFormat<OrderDto>> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusDto dto
    ) {
        OrderDto res = orderService.updateOrderStatus(orderId, dto);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Order status updated successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
