package com.codemarathon.subscription.flutter.dto.banktransfer;


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
    private BankTransferMeta meta;

}
