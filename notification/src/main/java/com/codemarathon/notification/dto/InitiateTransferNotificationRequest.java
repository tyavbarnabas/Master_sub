package com.codemarathon.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InitiateTransferNotificationRequest {

    private String toCustomerEmail;
    private String message;
}
