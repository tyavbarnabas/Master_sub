package com.codemarathon.notification.controller;

import com.codemarathon.notification.dto.NotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;
import com.codemarathon.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/send-notification")
    public NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest){
        log.info("in the notification controller...");
        return notificationService.sendNotification(notificationRequest);

    }
}
