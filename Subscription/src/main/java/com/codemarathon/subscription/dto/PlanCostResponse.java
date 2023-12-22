package com.codemarathon.subscription.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanCostResponse {

    private String responseCode;
    private String message;
    private Double cost;

}
