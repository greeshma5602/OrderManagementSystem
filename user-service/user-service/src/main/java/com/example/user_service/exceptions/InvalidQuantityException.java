package com.example.user_service.exceptions;

public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException() {
        super("Quantity Not Sufficient");
    }

    public InvalidQuantityException(String message) {
        super(message);
    }
}
