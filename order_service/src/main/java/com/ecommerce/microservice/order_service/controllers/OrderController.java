package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.aop.RoleAnnotation;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.dto.OrderDto;
import com.ecommerce.microservice.order_service.dto.OrderStatusDto;
import com.ecommerce.microservice.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Slf4j
@Tag(name = "Order Controller",description = "Order Controller for Ecommerce")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAllOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable Long userId) {
        try {
            var res = orderService.getAllOrdersbyUserId(userId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("error at the getallorders {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }


    @RoleAnnotation("ROLE_USER")
    @PostMapping("/placeOrder/{userId}/{addressId}/{amount}")
    public ResponseEntity<OrderDto> placeOrderByUser(@RequestBody InventoryClientRequestDto data, @PathVariable Long userId,
                                                     @PathVariable Long addressId,@PathVariable Double discount) {
        try {
            var res = orderService.placeOrder(data,addressId,userId,discount);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("error at the place order {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_USER")
    @PostMapping("/cancelOrder/{orderId}")
    public ResponseEntity<OrderDto> cancelOrderByOrderId(@PathVariable Long orderId) {
        try {
            var res = orderService.cancelOrder(orderId);
            return ResponseEntity.ok().body(res);
        }

        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getOrdersByProductId/{productId}")
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable Long productId) {
        try {
            System.out.println("the product id is " + productId);
            var res = orderService.getAllOrderByProductId(productId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/changeOrderStatus/{orderId}")
    public ResponseEntity<OrderDto> changeOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDto dto) {
        try {
            var res = orderService.updateOrderStatus(orderId,dto);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
