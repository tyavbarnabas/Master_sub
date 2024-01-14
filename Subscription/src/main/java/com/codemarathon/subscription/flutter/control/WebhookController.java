package com.codemarathon.subscription.flutter.control;

import com.codemarathon.subscription.dto.subDtos.SubscriptionRequest;
import com.codemarathon.subscription.flutter.dto.webhook.EvaluatePaymentResponse;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataRequest;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookResponse;
import com.codemarathon.subscription.flutter.service.WebhookService;
import com.codemarathon.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/flw-webhook")
    public WebhookResponse handleWebhook(@RequestBody WebhookDataRequest webhookDataRequest, @RequestHeader("verify-hash") String signature) {
        return subscriptionService.processWebhookEvent(webhookDataRequest,signature);
    }

    @PostMapping("/evaluate-payment")
    public EvaluatePaymentResponse evaluatePayment(@RequestBody SubscriptionRequest subscriptionRequest) {
        log.info("entering the payment subscription...");
        return subscriptionService.evaluatePayment(subscriptionRequest);
    }




}
