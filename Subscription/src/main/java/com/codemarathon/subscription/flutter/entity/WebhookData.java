package com.codemarathon.subscription.flutter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long data_id;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private WebhookCustomer customer;
    @Embedded
    private WebhookCard card;
}
