package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.dto.CartDto;
import com.ecommerce.microservice.order_service.mappers.CartMappers;
import com.ecommerce.microservice.order_service.model.Cart;
import com.ecommerce.microservice.order_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public CartDto getCartByUserId(Long userId) throws Exception {
        Cart existingCart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new Exception("Cart not found with userId : {}"+userId));

        return CartMappers.toCartDto(existingCart);
    }

    public CartDto deleteFromCart(Long productId,Long userId) throws Exception {
        Cart existingCart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new Exception("Cart not found with userId : {}"+userId));
        existingCart.getProductIds()
                .remove(productId);
        return CartMappers
                .toCartDto(cartRepository.save(existingCart));
    }

    public CartDto addToCart(CartDto dto) {
        Cart existingCart = cartRepository.findByUserId(dto.getUserId())
                .orElse(null);
        if (existingCart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(dto.getUserId());
            newCart.setProductIds(dto.getProductIds());

            return CartMappers.toCartDto(cartRepository.save(newCart));
        }
        List<Long> productId = existingCart.getProductIds();
        productId.addAll(dto.getProductIds());
        existingCart.setUserId(dto.getUserId());
        existingCart.setProductIds(productId);
        return CartMappers
                .toCartDto(cartRepository.save(existingCart));
    }
}
