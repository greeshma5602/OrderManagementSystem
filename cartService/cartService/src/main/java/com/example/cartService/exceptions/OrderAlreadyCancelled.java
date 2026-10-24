package com.example.cartService.exceptions;

public class OrderAlreadyCancelled extends RuntimeException {
    public OrderAlreadyCancelled(String message) {
        super(message);
    }
}
