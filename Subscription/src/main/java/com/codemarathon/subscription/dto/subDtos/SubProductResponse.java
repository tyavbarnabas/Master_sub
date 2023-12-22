package com.codemarathon.subscription.dto.subDtos;

import com.codemarathon.product.model.Product;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubProductResponse {

    private String responseCode;
    private String message;
    private Product details;
}
