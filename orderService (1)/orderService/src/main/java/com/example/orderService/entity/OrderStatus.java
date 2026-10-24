package com.example.orderService.entity;




public enum OrderStatus {
    CREATED,
    INVENTORY_CHECK_PENDING,
    CONFIRMED,
    OUT_OF_STOCK,
    PLACED, DELIVERED, SHIPPED, CANCELLED
}
