package com.cscie97.store.model;

/**
 * A generic command used by appliances
 * @author Tofik Mussa
 */
public class Command {

    private String message;

    /**
     *
     * @param message
     */
    public Command(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Command{" +
                "message='" + message + '\'' +
                '}';
    }
}
