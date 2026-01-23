package com.microservice.productservice.product_service.service;

import com.microservice.productservice.product_service.dto.review.ReviewDto;
import com.microservice.productservice.product_service.mapper.ProductMapper;
import com.microservice.productservice.product_service.mapper.ReviewMapper;
import com.microservice.productservice.product_service.model.ProductModel;
import com.microservice.productservice.product_service.model.ReviewModel;
import com.microservice.productservice.product_service.repository.ProductRepository;
import com.microservice.productservice.product_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewDto createReview(Long productId,ReviewDto reviewDto) throws Exception{
        ProductModel productModel = productRepository.findById(productId)
                .orElseThrow(()->new Exception("Product didn't found with id : "+productId));
        ReviewModel res = ReviewMapper.toReviewModel(reviewDto);
        res.setProduct(productModel);

        return ReviewMapper.toReviewDto(reviewRepository.save(res));
    }

    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
        return reviewRepository.findAllByProductId(productId)
                .stream()
                .map(ReviewMapper::toReviewDto).toList();
    }
}
