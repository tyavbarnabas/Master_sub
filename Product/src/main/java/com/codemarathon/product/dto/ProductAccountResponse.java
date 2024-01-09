package com.codemarathon.product.dto;

import com.codemarathon.product.model.ProductAccount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAccountResponse {

    private String responseCode;
    private String message;
    private ProductAccount productAccount;
}
