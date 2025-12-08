package com.notification_service.notification_service.repository;

import com.notification_service.notification_service.model.UserFcmTokenDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFcmTokenDetailsRepository extends JpaRepository<UserFcmTokenDetails,Long> {
    Optional<UserFcmTokenDetails> findByUserId(Long userId);
}
