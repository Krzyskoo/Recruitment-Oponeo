package com.example.e_commerce.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
