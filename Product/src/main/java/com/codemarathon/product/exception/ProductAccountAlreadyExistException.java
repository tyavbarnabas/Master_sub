package com.codemarathon.product.exception;

public class ProductAccountAlreadyExistException extends RuntimeException {
    public ProductAccountAlreadyExistException(String message) {
        super(message);
    }
}
