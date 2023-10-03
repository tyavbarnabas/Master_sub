package com.codemarathon.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageRequest {

    private String productId;
    private double amount;
    private String packageName;
    private String packageDescription;
}
