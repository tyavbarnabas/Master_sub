package com.codemarathon.subscription.flutter.dto.webhook;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookCustomerDTO {

    private Long id;
    private String name;
    private String phone_number;
    private String email;
    private String created_at;

}
