package com.codemarathon.subscription.flutter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookCard {

    private String first_6Digits;
    private String last_4Digits;
    private String issuer;
    private String country;
    private String type;
    private String expiry;
}
