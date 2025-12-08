package com.ecommerce.microservice.order_service.mappers;

import com.ecommerce.microservice.order_service.dto.AddressDto;
import com.ecommerce.microservice.order_service.model.Address;

import java.time.LocalDateTime;

public class AddressMappers {
    public static AddressDto addressDto(Address address) {
        AddressDto res = new AddressDto();
        res.setName(address.getName());
        res.setEmail(address.getEmail());
        res.setId(address.getId());
        res.setAddress(address.getAddress());
        res.setCity(address.getCity());
        res.setAddressType(address.getAddressType());
        res.setPinCode(address.getPinCode());
        res.setLatitude(address.getLatitude());
        res.setState(address.getState());
        res.setLongitude(address.getLongitude());
        res.setLandmark(address.getLandmark());
        res.setCreatedAt(address.getCreatedAt());
        res.setUpdatedAt(address.getUpdatedAt());
        res.setPhoneNumber(address.getPhoneNumber());
        res.setUserId(address.getUserId());
        return res;
    }


    public static Address address(AddressDto addressDto) {
        Address res = new Address();
        res.setName(addressDto.getName());
        res.setEmail(addressDto.getEmail());
        res.setAddress(addressDto.getAddress());
        res.setCity(addressDto.getCity());
        res.setAddressType(addressDto.getAddressType());
        res.setPinCode(addressDto.getPinCode());
        res.setLatitude(addressDto.getLatitude());
        res.setState(addressDto.getState());
        res.setLongitude(addressDto.getLongitude());
        res.setLandmark(addressDto.getLandmark());
        res.setUpdatedAt(LocalDateTime.now());
        res.setPhoneNumber(addressDto.getPhoneNumber());
        res.setUserId(addressDto.getUserId());
        return res;
    }
}
