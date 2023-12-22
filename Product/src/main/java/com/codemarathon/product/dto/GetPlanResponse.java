package com.codemarathon.product.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPlanResponse {

    private String responseCode;
    private String message;
    private PlanDetails planDetails;
}
