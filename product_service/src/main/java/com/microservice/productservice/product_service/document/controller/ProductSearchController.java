package com.microservice.productservice.product_service.document.controller;

import com.microservice.productservice.product_service.common.ApiResponseFormat;
import com.microservice.productservice.product_service.document.model.ProductDocument;
import com.microservice.productservice.product_service.document.repository.ProductDocumentRepository;
import com.microservice.productservice.product_service.document.service.ProductDocumentService;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product Search Controller", description = "ProductSearch Controller for Ecommerce")
public class ProductSearchController {

    private final ProductDocumentService productDocumentService;
    private final ProductDocumentRepository productDocumentRepository;
    private final Logger log = LoggerFactory.getLogger(ProductSearchController.class);

    @GetMapping("/searchByKeyword/{keyword}")
    public ResponseEntity<ApiResponseFormat<List<ProductCreateResponseDto>>> searchByKeyword(
            @PathVariable String keyword
    ) {
        List<ProductCreateResponseDto> res =
                productDocumentService.searchByKeyWord(keyword);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Products fetched by keyword",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponseFormat<List<ProductDocument>>> allContents() {
        Iterable<ProductDocument> all = productDocumentRepository.findAll();
        List<ProductDocument> res = new ArrayList<>();
        for (ProductDocument document : all) {
            res.add(document);
        }

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "All product documents fetched",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<ApiResponseFormat<Boolean>> deleteAllContents() {
        productDocumentRepository.deleteAll();

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        true,
                        "All product documents deleted",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
