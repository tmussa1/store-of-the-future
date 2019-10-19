package com.cscie97.store.model;

/**
 * This is a generic class for all sensor and appliance events
 * @author Tofik Mussa
 */
public class Event {

    private String message;

    /**
     *
     * @param message
     */
    public Event(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "message='" + message + '\'' +
                '}';
    }
}
