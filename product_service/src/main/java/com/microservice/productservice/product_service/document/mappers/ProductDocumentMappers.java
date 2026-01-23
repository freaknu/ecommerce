package com.microservice.productservice.product_service.document.mappers;


import com.microservice.productservice.product_service.document.model.CategoryDocument;
import com.microservice.productservice.product_service.document.model.ProductDocument;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductDocumentMappers {

    public static ProductDocument mapToProductDocument(ProductModel productModel) {
        if (productModel.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null when indexing");
        }
        ProductDocument doc = new ProductDocument();
        doc.setId(productModel.getId());
        doc.setProductName(productModel.getProductName());
        doc.setProductDescription(productModel.getProductDescription());
        doc.setProductPrice(productModel.getProductPrice());
        doc.setCreatedBy(productModel.getCreatedBy());
        doc.setCreatedAt(productModel.getCreatedAt());
        doc.setUpdatedAt(productModel.getUpdatedAt());
        doc.setImages(productModel.getProductsImages());

        if (productModel.getCategory() != null) {
            doc.setCategory(mapToCategoryDocument(productModel.getCategory()));
        }

        if (productModel.getSizes() != null) {
            doc.setSizes(
                    new ArrayList<>(productModel.getSizes())
            );
        }

        return doc;
    }

    public static CategoryDocument mapToCategoryDocument(CategoryModel categoryModel) {
        if (categoryModel == null) return null;

        CategoryDocument doc = new CategoryDocument();
        doc.setId(categoryModel.getId());
        doc.setUserId(categoryModel.getUserId());
        doc.setCategoryName(categoryModel.getCategoryName());
        doc.setCategoryDescription(categoryModel.getCategoryDescription());
        doc.setCategoryImage(categoryModel.getCategoryImage());
        doc.setCreatedAt(categoryModel.getCreatedAt());
        doc.setUpdatedAt(categoryModel.getUpdatedAt());
        return doc;
    }

    public static ProductCreateResponseDto toProductResponse(ProductDocument productDocument) {
        ProductCreateResponseDto res = new ProductCreateResponseDto();


        res.setCategoryId(productDocument.getCategory().getId());
        res.setProductName(productDocument.getProductName());
        res.setProductDescription(productDocument.getProductDescription());
        res.setSizes(productDocument.getSizes());
        res.setProductPrice(productDocument.getProductPrice());
        res.setId(productDocument.getId());
        res.setCategoryName(productDocument.getCategory().getCategoryName());
        res.setProductsImages(productDocument.getImages());

        return res;
    }
}
