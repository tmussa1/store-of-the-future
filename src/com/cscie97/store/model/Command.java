package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Command {

    private String message;

    public Command(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
