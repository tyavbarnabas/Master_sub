package com.codemarathon.subscription.flutter.exception;

public class InvalidSecretHash extends RuntimeException{
    public InvalidSecretHash(String message) {
        super(message);
    }
}
