package com.codemarathon.subscription.flutter.dto.subaccount;


import com.codemarathon.subscription.flutter.dto.subaccount.Meta;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Subaccount {
    private String account_bank;
    private String account_number;
    private String business_name;
    private String business_email;
    private String business_contact;
    private String business_contact_mobile;
    private String business_mobile;
    private String country;
    private List<Meta> meta;
    private String split_type;
    private double split_value;
}
