package com.notification_service.notification_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OtpEvent {
    private int otp;
    private String message;
    private String email;
}
