package com.microservice.productservice.product_service.document.repository;

import com.microservice.productservice.product_service.document.model.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument,Long> {
    List<ProductDocument>findByProductNameContaining(String keyword);
    List<ProductDocument> findByProductDescriptionContaining(String keyword);
    List<ProductDocument> findByCategory_CategoryNameContaining(String categoryName);
}
