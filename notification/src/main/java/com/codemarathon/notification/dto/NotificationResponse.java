package com.codemarathon.notification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private String responseCode;
    private String responseMessage;

}
