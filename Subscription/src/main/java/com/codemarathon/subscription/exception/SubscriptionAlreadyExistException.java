package com.codemarathon.subscription.exception;

public class SubscriptionAlreadyExistException extends RuntimeException{
    public SubscriptionAlreadyExistException(String message) {
        super(message);
    }
}
