package com.codemarathon.subscription.exception;

public class SubscriptionPaymentFailedException extends RuntimeException{
    public SubscriptionPaymentFailedException(String message) {
        super(message);
    }
}
