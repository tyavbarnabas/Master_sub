package com.codemarathon.notification.dto;


public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerEmail,
        String message
) {
}
