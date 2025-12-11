package com.microservice.productservice.product_service.document.repository;

import com.microservice.productservice.product_service.document.model.CategoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDocumentRepository extends ElasticsearchRepository<CategoryDocument,Long> {
}
