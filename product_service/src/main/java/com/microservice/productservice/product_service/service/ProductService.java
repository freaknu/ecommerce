package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.common.ResourceNotFoundException;
import com.microservice.productservice.product_service.document.service.ProductDocumentService;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.dto.product.ProductCreaterequestDto;
import com.microservice.productservice.product_service.mapper.ProductMapper;
import com.microservice.productservice.product_service.model.CategoryModel;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.repository.CategoryRepository;
import com.microservice.productservice.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryClientService inventoryClientService;
    private final ProductDocumentService productDocumentService;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductCreateResponseDto createProduct(ProductCreaterequestDto data, int quantity) {

        CategoryModel category = categoryRepository.findById(data.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found"));

        ProductModel product = ProductMapper.toProductModel(data, category);
        ProductModel savedProduct = productRepository.save(product);
        productDocumentService.indexProduct(savedProduct);
        inventoryClientService.createOrUpdateInventory(savedProduct, data.getCategoryId(), quantity);

        return ProductMapper.toProductCreateResponseDto(savedProduct);
    }

    public List<ProductCreateResponseDto> getAllProducts(int page,int size) {
        log.info("getting data from db");
        Pageable pageable = PageRequest.of(page,size, Sort.by("productName").ascending());
        return productRepository.findAll(pageable)
                .stream()
                .map(ProductMapper::toProductCreateResponseDto)
                .toList();
    }

    public ProductCreateResponseDto getById(Long id) {
        ProductModel productModel = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not found with id " + id));
        return ProductMapper.toProductCreateResponseDto(productModel);
    }

    public void deleteProduct(Long id) {
        productDocumentService.deleteProductFromIndex(id);
        productRepository.deleteById(id);
    }


    public ProductCreateResponseDto updateProduct(Long id, ProductCreaterequestDto data) {
        ProductModel productModel = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not found with id " + id));

        productModel.setProductName(data.getProductName());
        productModel.setProductsImages(data.getProductsImages());
        productModel.setProductDescription(data.getProductDescription());
        productModel.setProductPrice(data.getProductPrice());

        CategoryModel category = categoryRepository.findById(data.getCategoryId()).orElse(null);
        productModel.setCategory(category);

        ProductModel updatedProduct = productRepository.save(productModel);
        return ProductMapper.toProductCreateResponseDto(updatedProduct);
    }

    public List<ProductCreateResponseDto> getAllProductsByCategoryId(Long categoryId,int page,int size) {
        CategoryModel categoryModel = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Pageable pageable = PageRequest.of(page,size,Sort.by("productName").ascending());
        return productRepository.findAllByCategoryId(categoryId,pageable)
                .stream()
                .map(ProductMapper::toProductCreateResponseDto)
                .toList();
    }

    public List<ProductCreateResponseDto> getAllProductsByUserId(Long userId,int page,int size) {
        Pageable  pageable = PageRequest.of(page,size,Sort.by("productName").ascending());
        return productRepository.findAllByUserId(userId,pageable)
                .stream()
                .map(ProductMapper::toProductCreateResponseDto)
                .toList();
    }
}