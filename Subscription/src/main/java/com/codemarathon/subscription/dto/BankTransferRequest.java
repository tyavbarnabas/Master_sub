package com.codemarathon.subscription.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferRequest {
    private String tx_ref;
    private String amount;
    private String email;
    private String phone_number;
    private String currency;
    private String client_ip;
    private String device_fingerprint;
    private String narration;
    private boolean is_permanent;


}
