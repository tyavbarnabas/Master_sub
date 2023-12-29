package com.codemarathon.subscription.flutter.dto.banktransfer;

import com.codemarathon.subscription.flutter.dto.subaccount.Subaccount;
import lombok.*;

import java.util.List;

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
    private List<Subaccount> subaccounts;


}
