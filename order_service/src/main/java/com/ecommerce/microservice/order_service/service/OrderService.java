package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.clients.InventoryClient;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientRequestDto;
import com.ecommerce.microservice.order_service.clientsDto.InventoryClientResponse;
import com.ecommerce.microservice.order_service.dto.OrderDto;
import com.ecommerce.microservice.order_service.dto.OrderStatusDto;
import com.ecommerce.microservice.order_service.enums.OrderStatus;
import com.ecommerce.microservice.order_service.event.OrderPlacedEvent;
import com.ecommerce.microservice.order_service.mappers.OrderMappers;
import com.ecommerce.microservice.order_service.model.Address;
import com.ecommerce.microservice.order_service.model.Order;
import com.ecommerce.microservice.order_service.repository.AddressRepository;
import com.ecommerce.microservice.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressService  addressService;
    private final InventoryClientService inventoryClientService;
    private final KafkaEventService kafkaEventService;

    @Transactional
    public OrderDto placeOrder(InventoryClientRequestDto dto, Long addressId, Long userId) throws Exception {

        // Fetch address for order
        Address address = addressService.adddressByAddressId(addressId);
        if (address == null) {
            throw new Exception("Address not found with id: " + addressId);
        }

        // Check inventory availability
        InventoryClientResponse res = inventoryClientService.placedOrder(dto);
        log.info("Inventory service response: {}", res);

        if (res == null || !(res.getStatus() == 200 || res.getStatus() == 201)) {
            throw new Exception("Inventory rejected the order. Status: " +
                    (res != null ? res.getStatus() : "null response"));
        }

        Order order = new Order();
        order.setOrderAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setAddress(address);
        order.setUserId(userId);
        order.setProductId(dto.getProductId());
        order.setDeliveryDate(null);

        Order savedOrder = orderRepository.save(order);

        OrderPlacedEvent event = new OrderPlacedEvent();
        event.setOrderId(savedOrder.getId());
        event.setUserId(userId);

        kafkaEventService.placeOrderEvent(event);

        return OrderMappers.toOrderDto(savedOrder);
    }




    public OrderDto cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with id: " + orderId));

        order.setOrderStatus(OrderStatus.CANCELED);
        return OrderMappers.toOrderDto(orderRepository.save(order));
    }

    public List<OrderDto> getAllOrdersbyUserId(Long userId) {
        return orderRepository.findAllByUserId(userId)
                .stream()
                .map(OrderMappers::toOrderDto).toList();
    }

    public List<OrderDto> getAllOrderByProductId(Long productId) {
        return orderRepository
                .findAllByProductId(productId)
                .stream()
                .map(OrderMappers::toOrderDto)
                .toList();
    }

    public OrderDto updateOrderStatus(Long orderId, OrderStatusDto dto) {
        Order order = orderRepository.findById(orderId).get();
        if(order.getOrderStatus() == OrderStatus.CANCELED) return null;
        order.setOrderStatus(dto.getOrderStatus());
        order.setDeliveryDate(dto.getOrderDate());
        return OrderMappers.toOrderDto(orderRepository.save(order));
    }
}
