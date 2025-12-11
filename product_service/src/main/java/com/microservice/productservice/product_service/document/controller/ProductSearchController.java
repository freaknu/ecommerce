package com.microservice.productservice.product_service.document.controller;

import com.microservice.productservice.product_service.document.model.ProductDocument;
import com.microservice.productservice.product_service.document.repository.ProductDocumentRepository;
import com.microservice.productservice.product_service.document.service.ProductDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product Search Controller",description = "ProductSearch Controller for Ecommerce")
public class ProductSearchController {
    private final ProductDocumentService productDocumentService;
    private final ProductDocumentRepository productDocumentRepository;

    private final Logger log= LoggerFactory.getLogger(ProductSearchController.class);

    @GetMapping("/searchByKeyword/{keyword}")
    public ResponseEntity<List<ProductDocument>> searchByKeyword(@PathVariable String keyword){
        try{
            var res= productDocumentService.searchByKeyWord(keyword);
            return ResponseEntity.ok(res);
        }
        catch (Exception e){
            log.error("error while getting the product:{}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getAll")
    public List<ProductDocument>  allContents(){
        Iterable<ProductDocument> all=productDocumentRepository.findAll();
        List<ProductDocument>res=new ArrayList<>();
        for (ProductDocument document : all) {
            res.add(document);
        }
        return res;
    }
    
    @PostMapping("/deleteAll")
    public void deleteAllContents(){
        productDocumentRepository.deleteAll();
    }
}
