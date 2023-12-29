package com.codemarathon.notification.service;


import com.codemarathon.notification.dto.InitiateTransferNotificationRequest;
import com.codemarathon.notification.dto.NotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendNotification(NotificationRequest notificationRequest);

    NotificationResponse sendInitiateTransactionNotification(InitiateTransferNotificationRequest request);
}
