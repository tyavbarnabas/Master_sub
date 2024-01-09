package com.codemarathon.subscription.flutter.dto.webhook;

import com.codemarathon.subscription.flutter.entity.WebhookData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookDataRequest {

    private String event;
    private WebhookData data;
    private String eventType;
}
