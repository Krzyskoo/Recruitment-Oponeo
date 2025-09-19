package com.example.e_commerce.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException() {
        super();
    }

    public DuplicateProductException(String message) {
        super(message);
    }

    public DuplicateProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
