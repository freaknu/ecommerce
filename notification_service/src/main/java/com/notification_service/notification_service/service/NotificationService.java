package com.notification_service.notification_service.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.notification_service.notification_service.model.Notifications;
import com.notification_service.notification_service.repository.NotificationsRepository;
import com.notification_service.notification_service.repository.UserFcmTokenDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationsRepository notificationsRepository;
    private final UserFcmTokenDetailsRepository userFcmTokenDetailsRepository;
    private final FcmService fcmService;

    public boolean saveNotification(Notifications notifications) throws FirebaseMessagingException {
        Notifications newNotification = new Notifications();
        newNotification.setUserId(notifications.getUserId());
        newNotification.setTitle(notifications.getTitle());
        newNotification.setDescription(notifications.getDescription());
        newNotification.setAboutPage(notifications.getAboutPage());
        newNotification.setCreatedAt(LocalDateTime.now());
        newNotification.setReceivedAt(LocalDateTime.now());
        newNotification.setIconUrl(notifications.getIconUrl());
        Notifications saved =  notificationsRepository.save(newNotification);

        String token = userFcmTokenDetailsRepository.findByUserId(saved.getUserId()).get().getFcmToken();
        if(!Objects.equals(token, "string") && token != null && !token.isEmpty()) {
            sendMessage(token,notifications);
        }

        return true;
    }


    public List<Notifications> getAllNotifications(Long userId) {
        return notificationsRepository.findAllByUserId(userId);
    }

    public boolean sendMessage(String fcm,Notifications notifications) throws FirebaseMessagingException {
//        Notifications notifications = new Notifications();
        fcmService.sendNotification(notifications,fcm);
        return true;
    }
}
