package com.example.productservice.exceptions;

public class OrderAlreadyCancelled extends RuntimeException {
    public OrderAlreadyCancelled(String message) {
        super(message);
    }
}
