package com.codemarathon.subscription.flutter.control;

import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataRequest;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookResponse;
import com.codemarathon.subscription.flutter.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/webhook")
public class handleWebhookController {

    private final WebhookService webhookService;


    @PostMapping("/flw-webhook")
    public WebhookResponse handleWebhook(@RequestBody WebhookDataRequest webhookDataRequest, @RequestHeader("verify-hash") String signature) {
        return webhookService.processWebhookEvent(webhookDataRequest,signature);
    }


}
