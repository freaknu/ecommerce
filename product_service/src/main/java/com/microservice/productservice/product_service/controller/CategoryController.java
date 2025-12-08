package com.microservice.productservice.product_service.controller;

import com.microservice.productservice.product_service.dto.category.CategoryCreateRequestDto;
import com.microservice.productservice.product_service.dto.category.CategoryCreateResponseDto;
import com.microservice.productservice.product_service.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Category Controller",description = "Controllers for Category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getallCategory")
    public ResponseEntity<List<CategoryCreateResponseDto>> getAllCategories()
    {
        try {
            List<CategoryCreateResponseDto> res = categoryService.getAllCategory();
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception ex)
            {return ResponseEntity.badRequest().build();}
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryCreateResponseDto> createCategory(@RequestBody CategoryCreateRequestDto data)
    {
        try {
            log.info("the incoming user id is :{}",data.getUserId());
            CategoryCreateResponseDto res = categoryService.createCategory(data);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<CategoryCreateResponseDto> getCategoryById(@PathVariable Long id)
    {
        try {
            CategoryCreateResponseDto res = categoryService.getCategoryById(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateCategoryById/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Long id, @RequestBody CategoryCreateRequestDto data)
    {
        try {
            categoryService.updateCategory(id, data);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCategoryByUserId/{userId}")
    public ResponseEntity<List<CategoryCreateResponseDto>> getAllCategoryUserId(@PathVariable Long userId) {
        try {
            var res = categoryService.getAllCategoryUserId(userId);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
