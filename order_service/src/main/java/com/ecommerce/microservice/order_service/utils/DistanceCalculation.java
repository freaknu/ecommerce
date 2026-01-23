package com.ecommerce.microservice.order_service.utils;

import com.ecommerce.microservice.order_service.model.Address;
import com.ecommerce.microservice.order_service.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DistanceCalculation {

    private final AddressRepository addressRepository;

    private static final double EARTH_RADIUS_KM = 6371;

    public LocalDateTime getDeliveryDate(Long userId, Long adminUserId) {

        Address storeAddress = addressRepository.findAllByUserId(adminUserId)
                .stream()
                .filter(Address::getIsDefaultAddress)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Store address not found"));

        Address userAddress = addressRepository.findAllByUserId(userId)
                .stream()
                .filter(Address::getIsDefaultAddress)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User address not found"));

        double lat1 = storeAddress.getLatitude();
        double lon1 = storeAddress.getLongitude();

        double lat2 = userAddress.getLatitude();
        double lon2 = userAddress.getLongitude();

        double distanceKm = calculateDistance(lat1, lon1, lat2, lon2);

        long deliveryMinutes = estimateDeliveryMinutes(distanceKm);

        return LocalDateTime.now().plusMinutes(deliveryMinutes);
    }


    private double calculateDistance(double lat1, double lon1,
                                     double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private long estimateDeliveryMinutes(double distanceKm) {
        double speedKmPerHour = 30.0;

        double hours = distanceKm / speedKmPerHour;

        long minutes = (long) Math.ceil(hours * 60);

        return Math.max(minutes, 30);
    }
}
