package com.cscie97.store.controller;

public class StoreControllerServiceException extends Exception {
    private String message;

    public StoreControllerServiceException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
