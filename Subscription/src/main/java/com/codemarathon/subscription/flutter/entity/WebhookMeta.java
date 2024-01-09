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
public class WebhookMeta {


    private String originatoraccountnumber;

    private String originatorname;

    private String bankname;

    private String originatoramount;

}
