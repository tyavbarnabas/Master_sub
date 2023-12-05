package com.codemarathon.subscription.dto.subDtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponse {

    private String responseCode;
    private String message;
    private Object details;
}
