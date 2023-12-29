package com.codemarathon.subscription.flutter.dto.webhook;

import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookResponse {

    private String event;
    private WebhookDataDTO data;

}
