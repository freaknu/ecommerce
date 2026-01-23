package com.notification_service.notification_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "notifications",indexes = {
        @Index(name = "idx_notifications_user_id",columnList = "user_id")
})
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private Long userId;
    private String iconUrl;
    private String aboutPage;
    private LocalDateTime receivedAt;
    private LocalDateTime createdAt;
}
