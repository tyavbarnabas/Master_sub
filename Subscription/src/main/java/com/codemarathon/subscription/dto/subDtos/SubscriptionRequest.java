package com.codemarathon.subscription.dto.subDtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubscriptionRequest {

    private Long id;
    private Long userId;
    private Long productId;
    private Long planId;
    private String currency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
