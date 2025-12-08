package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.dto.AddressDto;
import com.ecommerce.microservice.order_service.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
@Tag(name = "Address Controller",description = "Address Controllers for Ecommerce")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddress(@PathVariable Long userId) {
        try {
            var res =  addressService.getAllAddress(userId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/createAddress")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        try {
            var res = addressService.saveAddress(addressDto);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/updateAddress")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto) {
        try {
            var res = addressService.updateAddress(addressDto);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteAddress/{adddressId}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long adddressId) {
        try {
            addressService.deleteAddress(adddressId);
            return ResponseEntity.ok().body(new AddressDto());

        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
