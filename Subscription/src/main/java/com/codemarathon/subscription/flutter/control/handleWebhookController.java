package com.codemarathon.subscription.flutter.control;

import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataDTO;
import com.codemarathon.subscription.flutter.entity.WebhookEventEntity;
import com.codemarathon.subscription.flutter.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class handleWebhookController {

    private final WebhookService webhookService;


    @PostMapping("/flw-webhook")
    public WebhookEventEntity handleWebhook(@RequestBody WebhookDataDTO payload, @RequestHeader("verif-hash") String signature) {
        return webhookService.processWebhookEvent("charge.completed",payload, signature);
    }
}
