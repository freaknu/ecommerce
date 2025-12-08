package com.notification_service.notification_service.dto;

import com.notification_service.notification_service.model.UserFcmTokenDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDetailsRequestDto {
    private Long userId;
    private String email;
    private String fcmToken;
}
