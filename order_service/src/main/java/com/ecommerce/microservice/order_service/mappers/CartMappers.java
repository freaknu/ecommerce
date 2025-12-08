package com.ecommerce.microservice.order_service.mappers;

import com.ecommerce.microservice.order_service.dto.CartDto;
import com.ecommerce.microservice.order_service.model.Cart;
import com.ecommerce.microservice.order_service.model.Order;

public class CartMappers {
    public static CartDto toCartDto(Cart cart) {
        CartDto res = new CartDto();
        res.setId(cart.getId());
        res.setProductIds(cart.getProductIds());
        res.setUserId(cart.getUserId());
        return res;
    }

    public static Cart toCartModel(CartDto dto) {
        Cart cart = new Cart();
        return cart;
    }
}
