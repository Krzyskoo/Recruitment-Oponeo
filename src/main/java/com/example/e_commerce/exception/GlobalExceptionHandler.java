package com.example.e_commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",  ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(OrderEmptyException.class)
    public ResponseEntity<Map<String, String>> handleOrderEmptyException(OrderEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(QuantityException.class)
    public ResponseEntity<Map<String, String>> handleQuantityException(QuantityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(TaxIdAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleTaxIdAlreadyExistException(TaxIdAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",  ex.getMessage()));
    }
    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity <Map<String, String>> handleDuplicateProductException(DuplicateProductException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",  ex.getMessage()));
    }
}
