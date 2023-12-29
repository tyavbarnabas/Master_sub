package com.codemarathon.subscription.flutter.dto.webhook;


import com.codemarathon.subscription.flutter.dto.webhook.WebhookCardDTO;
import com.codemarathon.subscription.flutter.dto.webhook.WebhookCustomerDTO;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookDataDTO {

    private Long id;
    private String tx_ref;
    private String flw_ref;
    private String device_fingerprint;
    private Integer amount;
    private String currency;
    private Integer charged_amount;
    private Double app_fee;
    private Double merchant_fee;
    private String processor_response;
    private String auth_model;
    private String ip;
    private String narration;
    private String status;
    private String payment_type;
    private String created_At;
    private Long account_id;

    private WebhookCustomerDTO customer;

    private WebhookCardDTO card;
}
