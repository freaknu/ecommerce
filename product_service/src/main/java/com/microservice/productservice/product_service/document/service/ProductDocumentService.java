package com.microservice.productservice.product_service.document.service;


import com.microservice.productservice.product_service.document.mappers.ProductDocumentMappers;
import com.microservice.productservice.product_service.document.model.ProductDocument;
import com.microservice.productservice.product_service.document.repository.ProductDocumentRepository;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.mapper.ProductMapper;
import com.microservice.productservice.product_service.model.ProductModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductDocumentService {
    private final ProductDocumentRepository productDocumentRepository;
    private final Logger log= LoggerFactory.getLogger(ProductDocumentService.class);
    @Transactional
    public void indexProduct(ProductModel productModel){
        ProductDocument productDocument = ProductDocumentMappers.mapToProductDocument(productModel);
       ProductDocument res= productDocumentRepository.save(productDocument);
       log.info(res.toString());
    }

    public void deleteProductFromIndex(Long  productId) {
        productDocumentRepository.deleteById(productId);
    }

    public List<ProductCreateResponseDto> searchByKeyWord(String keyword) {

        Set<ProductDocument> result = new HashSet<>();

        result.addAll(productDocumentRepository
                .findByProductNameContaining(keyword));

        result.addAll(productDocumentRepository
                .findByCategory_CategoryNameContaining(keyword));

        return result.stream()
                .map(ProductDocumentMappers::toProductResponse).toList();
    }

}
