package com.notification_service.notification_service.controller;

import com.notification_service.notification_service.dto.UserDetailsRequestDto;
import com.notification_service.notification_service.model.UserFcmTokenDetails;
import com.notification_service.notification_service.service.DetailsService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = {
        "http://136.111.69.14",
        "http://localhost:5173"
})

@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final DetailsService detailsService;

    @PostMapping("/addtoken")
    public ResponseEntity<UserFcmTokenDetails>createToken(@RequestBody UserFcmTokenDetails dto){
        try {
            var res= detailsService.addToken(dto);
            return ResponseEntity.ok(res);
        }
        catch (Exception e) {
            log.error("unable to add token",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/gettoken/{userId}")
    public ResponseEntity<UserFcmTokenDetails> getTokenDetails(@PathVariable Long userId) {
        try {
            var res = detailsService.getByUserId(userId);
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            log.error("Problem while getting the UserTokenDetails : {}",e.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}
