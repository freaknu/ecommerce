package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.dto.discount.DiscountDto;
import com.microservice.productservice.product_service.dto.product.ProductCreateResponseDto;
import com.microservice.productservice.product_service.mapper.DiscountMapper;
import com.microservice.productservice.product_service.mapper.ProductMapper;
import com.microservice.productservice.product_service.model.DisCountModel;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.repository.DiscountRepository;
import com.microservice.productservice.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final ProductService productService;
    private final ProductRepository  productRepository;

    public List<DiscountDto> getAll(){
        return discountRepository
                .findAll()
                .stream()
                .map(discountMapper::toDiscountDto)
                .toList();
    }
    @Transactional
    public DiscountDto createDiscount(DiscountDto dto) {
        DisCountModel discount = discountMapper.toDiscountModel(dto);
        discount.setCreatedAt(LocalDateTime.now());
        discount.setUpdatedAt(LocalDateTime.now());
        discount.setActive(true);
        discount = discountRepository.save(discount);

        if (discount.getAllProducts() != null) {
            for (ProductModel product : discount.getAllProducts()) {

                if (product.getAllDiscounts() == null) {
                    product.setAllDiscounts(new ArrayList<>());
                }

                product.getAllDiscounts().add(discount);
                product.setUpdatedAt(LocalDateTime.now());
            }

            productRepository.saveAll(discount.getAllProducts());
        }

        return discountMapper.toDiscountDto(discount);
    }

    public boolean deleteDiscount(UUID discountId) throws Exception{
        DisCountModel disCountModel = discountRepository.findById(discountId)
                .orElseThrow(()->  new Exception("Discount don't exists with id : "+discountId));
        disCountModel.setActive(false);
        return true;
    }

    @Transactional
    public DiscountDto updateDiscount(DiscountDto dto, UUID discountId) {

        DisCountModel existing = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + discountId));


        existing.setCouponName(dto.getCouponName());
        existing.setDiscountPercentage(dto.getDiscountPercentage());
        existing.setUpdatedAt(LocalDateTime.now());
        List<ProductModel> updatedProducts = productRepository.findAllById(dto.getAllProductsId());

        if (existing.getAllProducts() == null) {
            existing.setAllProducts(new ArrayList<>());
        }

        List<ProductModel> toRemove = new ArrayList<>(existing.getAllProducts());
        toRemove.removeAll(updatedProducts);

        for (ProductModel product : toRemove) {
            product.getAllDiscounts().remove(existing);
        }

        List<ProductModel> toAdd = new ArrayList<>(updatedProducts);
        toAdd.removeAll(existing.getAllProducts());

        for (ProductModel product : toAdd) {
            if (product.getAllDiscounts() == null) {
                product.setAllDiscounts(new ArrayList<>());
            }
            product.getAllDiscounts().add(existing);
        }

        existing.setAllProducts(updatedProducts);


        productRepository.saveAll(updatedProducts);
        discountRepository.save(existing);

        return discountMapper.toDiscountDto(existing);
    }

    public List<ProductCreateResponseDto> getProductsByDiscountId(UUID discountId)throws Exception {
        return discountRepository
                .findById(discountId)
                .orElseThrow(()->new Exception("Discount not present with Id"+discountId))
                .getAllProducts()
                .stream()
                .map(ProductMapper::toProductCreateResponseDto)
                .toList();
    }

    public List<DiscountDto> getAllDiscountsByProductId(Long id)throws Exception {
        return  productRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Product Not Found with id " + id))
                .getAllDiscounts()
                .stream()
                .filter(DisCountModel::isActive)
                .map(discountMapper::toDiscountDto)
                .toList();

    }
}
