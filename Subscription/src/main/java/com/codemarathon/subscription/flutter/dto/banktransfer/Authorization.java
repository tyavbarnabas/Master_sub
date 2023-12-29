package com.codemarathon.subscription.flutter.dto.banktransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    private String transfer_reference;
    private String transfer_account;
    private String transfer_bank;
    private String account_expiration;
    private String transfer_note;
    private double transfer_amount;
    private String mode;
}
