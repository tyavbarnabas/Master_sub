package com.codemarathon.subscription.flutter.dto.webhook;

import com.codemarathon.subscription.flutter.entity.WebhookData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class WebhookResponse {

    private String responseCode;
    private String message;
    private String event;
    private WebhookData data;
    private String eventType;
    private LocalDateTime receivedAt;

}
