package com.codemarathon.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequest {

    private String productId;
    private String planName;
    private String interval;
    private int duration;
    private String productCode;
    private double amount;
    private String currency;
    private String packageDescription;
}
