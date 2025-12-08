package com.notification_service.notification_service.service;

import com.notification_service.notification_service.model.UserFcmTokenDetails;
import com.notification_service.notification_service.repository.UserFcmTokenDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailsService {

    private final UserFcmTokenDetailsRepository userFcmTokenDetailsRepository;


    public UserFcmTokenDetails addToken(UserFcmTokenDetails req) {
        UserFcmTokenDetails detail = userFcmTokenDetailsRepository.findByUserId(req.getUserId())
                .orElse(new UserFcmTokenDetails());
        detail.setEmail(req.getEmail());
        detail.setFcmToken(req.getFcmToken());
        detail.setUserId(req.getUserId());
        return userFcmTokenDetailsRepository.save(detail);
    }

    public UserFcmTokenDetails getByUserId(Long userId) {
        return userFcmTokenDetailsRepository
                .findByUserId(userId)
                .orElseThrow(()->new RuntimeException("User not found with userid" + userId));
    }
}
