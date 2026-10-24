package com.example.user_service.exceptions;

public class UnauthorizationException extends RuntimeException{
    public UnauthorizationException(String message) {
        super(message);
    }
}
