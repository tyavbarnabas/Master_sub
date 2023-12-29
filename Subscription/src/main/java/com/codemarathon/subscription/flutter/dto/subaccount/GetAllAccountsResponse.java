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
public class GetAllAccountsResponse {
    private String status;
    private String message;
    private Meta meta;
    private List<Data> data;

}
