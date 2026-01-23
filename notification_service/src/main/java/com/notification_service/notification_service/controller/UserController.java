package com.notification_service.notification_service.controller;

import com.notification_service.notification_service.common.ApiResponseFormat;
import com.notification_service.notification_service.model.UserFcmTokenDetails;
import com.notification_service.notification_service.service.DetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = {
        "http://136.111.69.14",
        "http://localhost:5173",
        "https://ecommerce-web-puce-sigma.vercel.app",
        "http://34.58.229.119:5173"
})
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final DetailsService detailsService;

    @PostMapping("/addtoken")
    public ResponseEntity<ApiResponseFormat<UserFcmTokenDetails>> createToken(
            @RequestBody UserFcmTokenDetails dto
    ) {
        UserFcmTokenDetails res = detailsService.addToken(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseFormat<>(
                        res,
                        "FCM token saved successfully",
                        true,
                        HttpStatus.CREATED.value(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/gettoken/{userId}")
    public ResponseEntity<ApiResponseFormat<UserFcmTokenDetails>> getTokenDetails(
            @PathVariable Long userId
    ) {
        UserFcmTokenDetails res = detailsService.getByUserId(userId);

        return ResponseEntity.ok(
                new ApiResponseFormat<>(
                        res,
                        "FCM token fetched successfully",
                        true,
                        HttpStatus.OK.value(),
                        LocalDateTime.now()
                )
        );
    }
}
