package com.codemarathon.subscription.dto.subDtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubscriptionRequest {

    private Long userId;
    private String email;
    private String productId;
    private String interval;
    private Long planId;
    private Long duration;
    private String currency;
    private LocalDateTime startDate;
}
