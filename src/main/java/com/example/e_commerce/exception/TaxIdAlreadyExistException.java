package com.example.e_commerce.exception;

public class TaxIdAlreadyExistException extends RuntimeException{
    public TaxIdAlreadyExistException() {
        super();
    }

    public TaxIdAlreadyExistException(String message) {
        super(message);
    }

    public TaxIdAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
