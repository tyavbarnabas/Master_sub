package com.codemarathon.subscription.dto.subDtos;

import com.codemarathon.product.dto.PlanDetails;
import com.codemarathon.product.model.Product;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCheckResponse {

    private String responseCode;
    private String message;
    private Product details;
    private PlanDetails planDetails;
}
