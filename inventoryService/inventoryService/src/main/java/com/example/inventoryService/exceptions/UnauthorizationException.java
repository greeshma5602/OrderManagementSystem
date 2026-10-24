package com.example.inventoryService.exceptions;

public class UnauthorizationException extends RuntimeException{
    public UnauthorizationException(String message) {
        super(message);
    }
}
