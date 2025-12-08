package com.ecommerce.microservice.order_service.service;

import com.ecommerce.microservice.order_service.dto.AddressDto;
import com.ecommerce.microservice.order_service.mappers.AddressMappers;
import com.ecommerce.microservice.order_service.model.Address;
import com.ecommerce.microservice.order_service.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository  addressRepository;

    @Transactional
    public AddressDto saveAddress(AddressDto addressDto) {
        Address res = AddressMappers.address(addressDto);
        res.setUpdatedAt(LocalDateTime.now());
        return AddressMappers.addressDto(addressRepository.save(res));
    }

    @Transactional
    public AddressDto updateAddress(AddressDto addressDto) throws  Exception {

        Address existing = addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> new Exception("Address not found with id: " + addressDto.getId()));

        existing.setAddressType(addressDto.getAddressType());
        existing.setAddress(addressDto.getAddress());
        existing.setCity(addressDto.getCity());
        existing.setState(addressDto.getState());
        existing.setLandmark(addressDto.getLandmark());
        existing.setPinCode(addressDto.getPinCode());
        existing.setPhoneNumber(addressDto.getPhoneNumber());
        existing.setLatitude(addressDto.getLatitude());
        existing.setLongitude(addressDto.getLongitude());

        existing.setUpdatedAt(LocalDateTime.now());

        Address saved = addressRepository.save(existing);
        return AddressMappers.addressDto(saved);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public List<AddressDto> getAllAddress(Long userId) {
        return addressRepository
                .findByUserId(userId)
                .stream()
                .map(AddressMappers::addressDto).toList();
    }

    Address adddressByAddressId(Long addressId)throws Exception {
        return addressRepository
                .findById(addressId)
                .orElseThrow(()->new Exception("Address not found with address id : {}" + addressId));
    }
}
