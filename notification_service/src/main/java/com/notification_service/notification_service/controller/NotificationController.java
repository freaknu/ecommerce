package com.notification_service.notification_service.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.notification_service.notification_service.common.ApiResponseFormat;
import com.notification_service.notification_service.model.Notifications;
import com.notification_service.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notification Controller", description = "Notification Controller for Ecommerce App")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/getAllNotifications/{userId}")
    public ResponseEntity<ApiResponseFormat<List<Notifications>>> getAllNotifications(
            @PathVariable Long userId
    ) {
        List<Notifications> res = notificationService.getAllNotifications(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "Notifications fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<ApiResponseFormat<Boolean>> createNotification(
            @RequestBody Notifications notifications
    ) throws FirebaseMessagingException {
        boolean res = notificationService.saveNotification(notifications);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "Notification saved successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @PostMapping("/sendNotificationFcm/{fcm}")
    public ResponseEntity<ApiResponseFormat<Boolean>> sendFcm(
            @PathVariable String fcm,
            @RequestBody Notifications notifications
    ) throws FirebaseMessagingException {

        boolean res = notificationService.sendMessage(fcm, notifications);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "FCM notification sent successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
