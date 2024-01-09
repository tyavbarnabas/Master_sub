package com.codemarathon.subscription.flutter.service;

import com.codemarathon.product.constants.GeneralResponseEnum;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataRequest;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookResponse;
import com.codemarathon.subscription.flutter.entity.WebhookEntity;
import com.codemarathon.subscription.flutter.exception.InvalidSecretHash;
import com.codemarathon.subscription.flutter.repository.WebhookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebhookService {


    private final WebhookRepository webhookRepository;

    @Value("${master.sub.bank.flutter.secret.hash}")
    private String staticSecretHash;

    public WebhookResponse processWebhookEvent(WebhookDataRequest webhookDataRequest,String signature) {

        if (!signature.equals(staticSecretHash)) {

            log.info("Generated Secret Hash: " + staticSecretHash);
            throw new InvalidSecretHash("invalid secret hash");
        }

        log.info("Received webhook payload: {}", webhookDataRequest);

        WebhookEntity webhookEvent = new WebhookEntity();

        webhookEvent.setEvent(webhookDataRequest.getEvent());
        webhookEvent.setData(webhookDataRequest.getData());
        webhookEvent.setEvent_type(webhookDataRequest.getEventType());
        webhookEvent.setReceived_at(LocalDateTime.now());

        webhookRepository.save(webhookEvent);

        return WebhookResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .event(webhookEvent.getEvent())
                .data(webhookEvent.getData())
                .eventType(webhookEvent.getEvent_type())
                .receivedAt(webhookEvent.getReceived_at())
                .build();
    }

    public String generateSecretHash() {
        int length = 32;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }




}
