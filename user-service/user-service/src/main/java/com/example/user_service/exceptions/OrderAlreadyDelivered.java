package com.example.user_service.exceptions;

public class OrderAlreadyDelivered extends RuntimeException{
    public OrderAlreadyDelivered(String message
    ) {
        super(message);
    }
}
