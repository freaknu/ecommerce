package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.dto.PaymentDto;
import com.ecommerce.microservice.order_service.enums.OrderStatus;
import com.ecommerce.microservice.order_service.mappers.PaymentMapper;
import com.ecommerce.microservice.order_service.model.Order;
import com.ecommerce.microservice.order_service.model.Payment;
import com.ecommerce.microservice.order_service.repository.OrderRepository;
import com.ecommerce.microservice.order_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentDto pay(PaymentDto dto) throws Exception {

        Order existingOrder = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new Exception("Order not found with orderId : " + dto.getOrderId()));

        if (existingOrder.getPayment() != null && Boolean.TRUE.equals(existingOrder.getPayment().getIsPaid())) {
            throw new Exception("Order is already paid");
        }

        Payment payment = PaymentMapper.toPayment(dto);
        payment.setIsPaid(true);
        payment.setPayDateTime(LocalDateTime.now());

        payment.setOrder(existingOrder);
        existingOrder.setPayment(payment);
        existingOrder.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(existingOrder);

        return PaymentMapper.toPaymentDto(existingOrder.getPayment());
    }


    public PaymentDto getPaymentByOrderId(Long orderId) {
        return PaymentMapper.toPaymentDto(orderRepository.findById(orderId).get().getPayment());
    }


    public List<PaymentDto> getPaymentByUserId(Long userId) {
        return paymentRepository.findByBuyerId(userId).stream()
                .map(PaymentMapper::toPaymentDto).toList();
    }

    public List<PaymentDto> getPaymentByProductId(Long productId) {
        return paymentRepository.findByProductId(productId)
                .stream().map(PaymentMapper::toPaymentDto).toList();
    }

    public List<PaymentDto> getPaymentBySellerId(Long sellerId) {
        return paymentRepository.findBySellerId(sellerId)
                .stream()
                .map(PaymentMapper::toPaymentDto).toList();
    }

    @Transactional
    public void deleteAll() {
        paymentRepository.deleteAll();
        log.info("all deleted successfully");
    }
}
