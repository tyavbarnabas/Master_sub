package com.codemarathon.subscription.flutter.service;

import com.codemarathon.subscription.flutter.dto.webhook.WebhookDataDTO;
import com.codemarathon.subscription.flutter.entity.WebhookData;
import com.codemarathon.subscription.flutter.entity.WebhookEventEntity;
import com.codemarathon.subscription.flutter.exception.InvalidSecretHash;
import com.codemarathon.subscription.flutter.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebhookService {


    private final WebhookEventRepository webhookEventRepository;

    public WebhookEventEntity processWebhookEvent(String eventType, WebhookDataDTO payload, String signature) {

        int secretHashLength = 32;
        String secretHash = generateSecretHash(secretHashLength);

        log.info("Generated Secret Hash: " + secretHash);

        if (!signature.equals(secretHash)) {
            throw new InvalidSecretHash("invalid secret hash");
        }

        log.info("Received webhook payload: {}", payload);

        WebhookEventEntity webhookEvent = new WebhookEventEntity();
        webhookEvent.setEvent(eventType);

        WebhookData webhookData = convertDtoToEntity(payload);
        webhookEvent.setData(webhookData);

        webhookEvent.setReceivedAt(LocalDateTime.now());
        webhookEventRepository.save(webhookEvent);

        return webhookEvent;
    }

    private WebhookData convertDtoToEntity(WebhookDataDTO dto) {

        WebhookData entity = new WebhookData();

        entity.setId(dto.getId());
        entity.setTx_ref(dto.getTx_ref());
        entity.setFlw_ref(dto.getFlw_ref());
        entity.setDevice_fingerprint(dto.getDevice_fingerprint());
        entity.setAmount(dto.getAmount());
        entity.setCurrency(entity.getCurrency());
        entity.setCharged_amount(entity.getCharged_amount());
        entity.setApp_fee(dto.getApp_fee());
        entity.setMerchant_fee(dto.getMerchant_fee());
        entity.setProcessor_response(dto.getProcessor_response());
        entity.setAuth_model(dto.getAuth_model());
        entity.setIp(dto.getIp());
        entity.setNarration(dto.getNarration());
        entity.setStatus(dto.getStatus());
        entity.setPayment_type(dto.getPayment_type());
        entity.setCreated_At(dto.getCreated_At());
        entity.setAccount_id(dto.getAccount_id());

        return entity;
    }


    public static String generateSecretHash(int length) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }




}
