package com.example.user_service.exceptions;

public class OrderAlreadyCancelled extends RuntimeException {
    public OrderAlreadyCancelled(String message) {
        super(message);
    }
}
