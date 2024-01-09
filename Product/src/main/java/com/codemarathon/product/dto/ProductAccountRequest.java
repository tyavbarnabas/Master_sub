package com.codemarathon.product.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAccountRequest {

    private String productCode;
    private String accountBank;
    private String accountNumber;
    private String businessName;
    private String businessEmail;
    private String businessContact;
    private String businessContactMobile;
    private String businessMobile;
    private String country;
}
