package com.example.cartService.exceptions;

public class UnauthorizationException extends RuntimeException{
    public UnauthorizationException(String message) {
        super(message);
    }
}
