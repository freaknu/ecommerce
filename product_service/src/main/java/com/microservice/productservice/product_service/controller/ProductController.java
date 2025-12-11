package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.aop.RoleAnnotation;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreaterequestDto;
import com.microservice.productservice.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product Controller",description = "Controller for Product")
public class ProductController {
    private final ProductService productService;
    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    @GetMapping("/getallProducts")
    public ResponseEntity<List<ProductCreateResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10")  int size
    ) {
        try {
            List<ProductCreateResponseDto> allProducts = productService.getAllProducts(page,size);
            return ResponseEntity.ok(allProducts);
        }
        catch (Exception e) {
            log.error("error while getting the products ->" + e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/createProduct/{quantity}")
    public ResponseEntity<ProductCreateResponseDto> createProduct(@RequestBody ProductCreaterequestDto data,@PathVariable int quantity) {
        try {
            ProductCreateResponseDto res = productService.createProduct(data,quantity);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            throw e;
        }
    }
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ProductCreateResponseDto> getProductById(@PathVariable Long id) {
        try {
            ProductCreateResponseDto data = productService.getById(id);
            return ResponseEntity.ok(data);
        }catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_ADMIN")
    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<ProductCreateResponseDto>  updateProduct(@PathVariable Long id, @RequestBody ProductCreaterequestDto data) {
        try {
            var res = productService.updateProduct(id,data);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @RoleAnnotation("ROLE_ADMIN")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Boolean>  deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getallProductsByCategory/{id}")
    public ResponseEntity<List<ProductCreateResponseDto>> getAllProductsByCategory(@PathVariable Long id,
                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size) {
        try {
            List<ProductCreateResponseDto> res = productService.getAllProductsByCategoryId(id,page,size);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<List<ProductCreateResponseDto>> getAllProductsByUserId(@PathVariable Long userId,
                                                                                 @RequestParam(defaultValue = "0")int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        try {
            var res = productService.getAllProductsByUserId(userId,page,size);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
