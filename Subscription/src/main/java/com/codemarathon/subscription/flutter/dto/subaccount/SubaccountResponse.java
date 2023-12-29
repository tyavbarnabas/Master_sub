package com.codemarathon.subscription.flutter.dto.subaccount;

import com.codemarathon.subscription.flutter.dto.subaccount.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubaccountResponse {
    private String status;
    private String message;
    private Data data;
}
