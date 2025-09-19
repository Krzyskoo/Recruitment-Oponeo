package com.example.e_commerce.exception;

public class QuantityException extends RuntimeException{
    public QuantityException() {
        super();
    }

    public QuantityException(String message) {
        super(message);
    }

    public QuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
