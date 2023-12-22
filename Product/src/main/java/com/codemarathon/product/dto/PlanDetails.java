package com.codemarathon.product.dto;

import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDetails {

    private Long Id;
    private String packageName;
    private String interval;
    private String productCode;
    private double amount;
    private String currency;
    private String packageDescription;

}
