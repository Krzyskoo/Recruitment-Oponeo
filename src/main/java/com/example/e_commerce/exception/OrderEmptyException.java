package com.example.e_commerce.exception;

public class OrderEmptyException extends RuntimeException{
    public OrderEmptyException() {
        super();
    }

    public OrderEmptyException(String message) {
        super(message);
    }

    public OrderEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
