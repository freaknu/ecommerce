package com.ecommerce.microservice.order_service.mappers;

import com.ecommerce.microservice.order_service.dto.OrderDto;
import com.ecommerce.microservice.order_service.enums.OrderStatus;
import com.ecommerce.microservice.order_service.model.Order;

public class OrderMappers {
    public static Order toOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setOrderAt(orderDto.getOrderAt());
        return order;
    }

    public static OrderDto toOrderDto(Order order) {
        OrderDto res = new OrderDto();
        res.setId(order.getId());
        res.setUserId(order.getUserId());
        res.setOrderAt(order.getOrderAt());
        res.setDeliveryDate(order.getDeliveryDate());
        res.setOrderStatus(order.getOrderStatus());
        res.setOrderDate(order.getOrderAt());
        res.setProductId(order.getProductId());
        res.setAddress(order.getAddress());
        return res;
    }
}
