package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.aop.RoleAnnotation;
import com.microservice.productservice.product_service.config.ContextFactory;
import com.microservice.productservice.product_service.dto.discount.DiscountDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.service.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Discount Controller",description = "Discount Controller for Ecommerce")
public class DiscountController {
    private final DiscountService discountService;
    private final Logger log = LoggerFactory.getLogger(DiscountController.class);
    private final ContextFactory contexts;

    @GetMapping("/getAllDiscounts")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {
        try {
            var res = discountService.getAll();
            contexts.getRoles();
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while getting the all discounts : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/createDiscount")
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
        try {
            var res = discountService.createDiscount(discountDto);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while creating the discount : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/updateDiscount/{id}")
    public ResponseEntity<DiscountDto> updateDiscount(@RequestBody DiscountDto discountDto,@PathVariable UUID id) {
        try {
            var res = discountService.updateDiscount(discountDto,id);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while updating the discount : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_ADMIN")
    @DeleteMapping("/deleteDiscount/{id}")
    public ResponseEntity<Boolean> deleteDiscount(@PathVariable UUID id) {
        try {
            var res = discountService.deleteDiscount(id);
            return ResponseEntity.ok().body(res);
        }
        catch(Exception e) {
            log.error("Problem while deleting the discount : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/findByDiscountId/{id}")
        public ResponseEntity<List<ProductCreateResponseDto>>  getAllProductsByDiscount(@PathVariable UUID id) {
        try {
            var res = discountService.getProductsByDiscountId(id);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while getting the products : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/findByProductId/{id}")
    public ResponseEntity<List<DiscountDto>>  getAllProductsByDiscount(@PathVariable Long id) {
        try {
            var res = discountService.getAllDiscountsByProductId(id);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while getting the discounts : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }


}
