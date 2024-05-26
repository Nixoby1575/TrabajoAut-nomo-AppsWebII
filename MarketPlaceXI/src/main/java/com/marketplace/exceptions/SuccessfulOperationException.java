package com.marketplace.exceptions;

public class SuccessfulOperationException extends RuntimeException{
    public SuccessfulOperationException(String message) {
        super(message);
    }
}
