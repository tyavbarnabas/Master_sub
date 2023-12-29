package com.codemarathon.subscription.flutter.dto.webhook;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WebhookCardDTO {

    private String id;
    private String first_6Digits;
    private String last_4Digits;
    private String issuer;
    private String country;
    private String type;
    private String expiry;
}
