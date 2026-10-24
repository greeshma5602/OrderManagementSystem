package com.example.user_service.exceptions;

public class InvalidOrderState  extends RuntimeException{
    public  InvalidOrderState(){
        super("Order State is notCorrect");
    }

    public InvalidOrderState(String message) {
        super(message);
    }
}
