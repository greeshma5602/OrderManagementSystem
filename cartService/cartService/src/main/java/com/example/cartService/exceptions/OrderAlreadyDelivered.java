package com.example.cartService.exceptions;

public class OrderAlreadyDelivered extends RuntimeException{
    public OrderAlreadyDelivered(String message
    ) {
        super(message);
    }
}
