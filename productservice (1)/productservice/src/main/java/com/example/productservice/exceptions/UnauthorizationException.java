package com.example.productservice.exceptions;

public class UnauthorizationException extends RuntimeException{
    public UnauthorizationException(String message) {
        super(message);
    }
}
