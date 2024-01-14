package com.codemarathon.subscription.flutter.dto.webhook;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluatePaymentResponse {
    private String responseCode;
    private String message;

    public static EvaluatePaymentResponse successfulPayment(String message) {
        return new EvaluatePaymentResponse("000", message);
    }

    public static EvaluatePaymentResponse incompletePayment(String message) {
        return new EvaluatePaymentResponse("001", message);
    }

    public static EvaluatePaymentResponse paymentNotSuccessful(String message) {
        return new EvaluatePaymentResponse("002", message);
    }
}