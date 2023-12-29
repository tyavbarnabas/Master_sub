package com.codemarathon.subscription.flutter.dto.webhook;

import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookEventDTO {

    private String event;
    private WebhookDataDTO data;
}
