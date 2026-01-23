package com.notification_service.notification_service.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.notification_service.notification_service.model.Notifications;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class FcmService {

    public String sendNotification(Notifications notifications, String token)
            throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(notifications.getTitle())
                .setBody(notifications.getDescription())
                .setImage(notifications.getIconUrl())
                .build();

        Map<String, String> data = new ConcurrentHashMap<>();

        if (notifications.getAboutPage() != null) {
            data.put("page", notifications.getAboutPage());
        }

        if (notifications.getReceivedAt() != null) {
            data.put("receivedAt", notifications.getReceivedAt().toString());
        }

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(data)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }
}
