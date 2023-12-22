package com.codemarathon.subscription.dto.subDtos;


import com.codemarathon.subscription.model.Subscription;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponse {

    private String responseCode;
    private String message;
    private Subscription subscriptionDetails;
}
