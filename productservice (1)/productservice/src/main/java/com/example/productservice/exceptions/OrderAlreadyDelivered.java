package com.example.productservice.exceptions;

public class OrderAlreadyDelivered extends RuntimeException{
    public OrderAlreadyDelivered(String message
    ) {
        super(message);
    }
}
