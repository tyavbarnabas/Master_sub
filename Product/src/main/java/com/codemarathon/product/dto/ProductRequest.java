package com.codemarathon.product.dto;

import com.codemarathon.product.model.Address;
import com.codemarathon.product.model.Plan;
import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private String productCode;
    private String productDescription;
    private Address address;
    private List<Plan> plans;

}
