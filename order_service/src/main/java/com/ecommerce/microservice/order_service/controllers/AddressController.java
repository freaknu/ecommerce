package com.ecommerce.microservice.order_service.controllers;

import com.ecommerce.microservice.order_service.common.ApiResponseFormat;
import com.ecommerce.microservice.order_service.dto.AddressDto;
import com.ecommerce.microservice.order_service.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/order")
@Tag(name = "Address Controller", description = "Address Controllers for Ecommerce")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<ApiResponseFormat<List<AddressDto>>> getAllAddress(
            @PathVariable Long userId
    ) {
        List<AddressDto> res = addressService.getAllAddress(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Addresses fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/createAddress")
    public ResponseEntity<ApiResponseFormat<AddressDto>> createAddress(
            @RequestBody AddressDto addressDto
    ) {
        AddressDto res = addressService.saveAddress(addressDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Address created successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/updateAddress")
    public ResponseEntity<ApiResponseFormat<AddressDto>> updateAddress(
            @RequestBody AddressDto addressDto
    ) throws Exception {
        AddressDto res = addressService.updateAddress(addressDto);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Address updated successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @DeleteMapping("/deleteAddress/{adddressId}")
    public ResponseEntity<ApiResponseFormat<Void>> deleteAddress(
            @PathVariable Long adddressId
    ) {
        addressService.deleteAddress(adddressId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        null,
                        "Address deleted successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/setDefaultAddress/{userId}/{addressId}")
    public ResponseEntity<ApiResponseFormat<Boolean>> setDefaultAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        Boolean res = addressService.setDefaultAddress(userId, addressId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Default address updated",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
