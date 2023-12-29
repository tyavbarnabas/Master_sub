package com.codemarathon.subscription.flutter.dto.subaccount;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Data {

    private long id;
    private String account_number;
    private String account_bank;
    private String full_name;
    private String created_at;
    private String split_type;
    private double split_value;
    private String subaccount_id;
    private String bank_name;
}
