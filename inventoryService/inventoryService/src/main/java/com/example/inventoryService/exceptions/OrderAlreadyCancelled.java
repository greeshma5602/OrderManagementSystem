package com.example.inventoryService.exceptions;

public class OrderAlreadyCancelled extends RuntimeException {
    public OrderAlreadyCancelled(String message) {
        super(message);
    }
}
