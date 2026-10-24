package com.example.cartService.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException() {
        super("Invalid Email and Password");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
