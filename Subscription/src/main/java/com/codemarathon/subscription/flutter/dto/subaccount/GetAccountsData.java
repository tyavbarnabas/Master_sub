package com.codemarathon.subscription.flutter.dto.subaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountsData {

    private int id;
    private String account_number;
    private String account_bank;
    private String business_name;
    private String full_name;
    private String created_at;
    private List<MetaDetail> meta;
    private int account_id;
    private double split_ratio;
    private String split_type;
    private double split_value;
    private String subaccount_id;
    private String bank_name;
    private String country;

}
