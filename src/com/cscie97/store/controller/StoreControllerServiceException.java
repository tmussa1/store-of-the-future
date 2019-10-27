package com.cscie97.store.controller;

/**
 * An exception thrown by the store controller service
 * @author Tofik Mussa
 */
public class StoreControllerServiceException extends Exception {
    private String message;

    public StoreControllerServiceException(String message) {
        super(message);
    }
}
