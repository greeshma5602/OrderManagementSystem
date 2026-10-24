package com.example.inventoryService.exceptions;

public class InvalidOrderState  extends RuntimeException{
    public  InvalidOrderState(){
        super("Order State is notCorrect");
    }

    public InvalidOrderState(String message) {
        super(message);
    }
}
