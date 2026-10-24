package com.example.cartService.exceptions;

public class NotFoundException extends RuntimeException {

    private String entity;


    public NotFoundException() {
        super("Resource not found");
    }


    public NotFoundException(String entity) {
        super(entity + " not found");
        this.entity = entity;
    }


    public NotFoundException(String entity, String message) {
        super(message);
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }
}