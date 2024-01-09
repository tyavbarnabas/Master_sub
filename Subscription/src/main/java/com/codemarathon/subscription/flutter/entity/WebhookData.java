package com.codemarathon.subscription.flutter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WebhookData {
    @Column(name = "tx_ref")
    private String tx_ref;
    @Column(name = "flw_ref")
    private String flw_ref;
    @Column(name = "device_fingerprint")
    private String device_fingerprint;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "charge_amount")
    private Integer charged_amount;
    @Column(name = "app_fee")
    private Double app_fee;
    @Column(name = "merchant_fee")
    private Double merchant_fee;
    @Column(name = "processor_response")
    private String processor_response;
    @Column(name = "auth_model")
    private String auth_model;
    @Column(name = "ip")
    private String ip;
    @Column(name = "narration")
    private String narration;
    @Column(name = "status")
    private String status;
    @Column(name = "payment_type")
    private String payment_type;
    @Column(name = "created_at", insertable = false, updatable = false)
    private String created_at;
    @Column(name = "account_id")
    private Long account_id;
    private Customer customer;
    private WebhookMeta meta;

}
