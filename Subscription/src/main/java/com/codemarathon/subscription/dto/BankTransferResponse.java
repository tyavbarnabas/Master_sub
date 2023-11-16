package com.codemarathon.subscription.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankTransferResponse {
    private String status;
    private String message;
    private Meta meta;

}
