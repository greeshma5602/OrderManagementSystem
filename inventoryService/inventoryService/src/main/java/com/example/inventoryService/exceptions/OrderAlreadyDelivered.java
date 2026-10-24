package com.example.inventoryService.exceptions;

public class OrderAlreadyDelivered extends RuntimeException{
    public OrderAlreadyDelivered(String message
    ) {
        super(message);
    }
}
