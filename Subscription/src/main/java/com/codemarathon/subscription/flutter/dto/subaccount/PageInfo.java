package com.codemarathon.subscription.flutter.dto.subaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    private int total;
    private int current_page;
    private int total_pages;

}
