package com.codemarathon.subscription.exception;

public class BankTransferException extends RuntimeException{

    public BankTransferException(String message) {
        super(message);
    }
}
