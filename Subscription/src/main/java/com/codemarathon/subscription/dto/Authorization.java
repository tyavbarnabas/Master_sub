package com.codemarathon.subscription.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    private String transfer_reference;
    private String transfer_account;
    private String transfer_bank;
    private long account_expiration;
    private String transfer_note;
    private String transfer_amount;
    private String mode;
}
