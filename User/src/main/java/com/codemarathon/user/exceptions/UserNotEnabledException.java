package com.codemarathon.user.exceptions;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(String message) {
        super(message);
    }
}
