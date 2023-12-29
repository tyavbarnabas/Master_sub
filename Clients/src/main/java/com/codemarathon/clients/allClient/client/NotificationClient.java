package com.codemarathon.clients.allClient.client;

import com.codemarathon.notification.dto.InitiateTransferNotificationRequest;
import com.codemarathon.notification.dto.NotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification")
public interface NotificationClient {

    @PostMapping(path = "api/v1/notification/send-notification")
    NotificationResponse sendNotification(@RequestBody NotificationRequest notificationRequest);

    @PostMapping(path = "api/v1/send-intiate-transaction/notification")
    NotificationResponse sendInitiateTransactionNotification(@RequestBody InitiateTransferNotificationRequest
                                                                            request);
}
