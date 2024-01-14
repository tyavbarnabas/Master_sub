package com.codemarathon.subscription.exception;

public class WebhookNotFoundException extends RuntimeException{
    public WebhookNotFoundException(String message) {
        super(message);
    }
}
