package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.dto.PaymentDetailsDto;
import com.ecommerce.microservice.order_service.dto.PaymentDto;
import com.ecommerce.microservice.order_service.mappers.PaymentDetailsMapper;
import com.ecommerce.microservice.order_service.model.PaymentDetails;
import com.ecommerce.microservice.order_service.repository.PaymentDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentDetailsService {
    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsDto addOrModifyPaymentDetails(PaymentDetailsDto paymentDetailsDto) {
        PaymentDetails paymentDetails = paymentDetailsRepository.findBySellerId(paymentDetailsDto.getSellerId())
                .orElse(new PaymentDetails());
            paymentDetails.setPaymentModes(paymentDetailsDto.getPaymentModes());
            paymentDetails.setSellerId(paymentDetailsDto.getSellerId());
            paymentDetails.setSellerQrCodeUrl(paymentDetailsDto.getSellerQrCodeUrl());
            paymentDetails.setSellerUpi(paymentDetailsDto.getSellerUpi());
            return PaymentDetailsMapper.toPaymentDetailsDto(paymentDetailsRepository.save(paymentDetails));

    }

    public PaymentDetailsDto getPaymentDetail(Long sellerId)throws Exception {
       PaymentDetails paymentDetails = paymentDetailsRepository.findBySellerId(sellerId)
               .orElseThrow(()->new Exception("Payment details didn't found with seller id : "+sellerId));
       return PaymentDetailsMapper.toPaymentDetailsDto(paymentDetails);
    }
}
