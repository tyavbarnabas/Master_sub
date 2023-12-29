package com.codemarathon.notification.serviceImpl;

import com.codemarathon.notification.constants.GeneralResponseEnum;
import com.codemarathon.notification.dto.InitiateTransferNotificationRequest;
import com.codemarathon.notification.dto.NotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;
import com.codemarathon.notification.model.Notification;
import com.codemarathon.notification.repository.NotificationRepository;
import com.codemarathon.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("Master_sub")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
        return NotificationResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .responseMessage(GeneralResponseEnum.SUCCESS.getMessage())
                .build();
    }

    @Override
    public NotificationResponse sendInitiateTransactionNotification(InitiateTransferNotificationRequest request) {
        notificationRepository.save(Notification.builder()
                        .toCustomerEmail(request.getToCustomerEmail())
                        .sender("Master_sub")
                        .message(request.getMessage())
                        .sentAt(LocalDateTime.now())
                .build());
        return NotificationResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .responseMessage(GeneralResponseEnum.SUCCESS.getMessage())
                .build();
    }

}
