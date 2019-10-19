package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Microphone implements ISensor {

    private String sensorId;
    private String sensorName;
    private InventoryLocation location;
    private String type;

    /**
     *
     * @param sensorId
     * @param sensorName
     * @param location
     */
    public Microphone(String sensorId, String sensorName, InventoryLocation location) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.location = location;
        this.type = this.getClass().getName();
    }

    @Override
    public String getSensorId() {
        return sensorId;
    }

    @Override
    public String getSensorName() {
        return sensorName;
    }

    @Override
    public InventoryLocation getSensorLocation() {
        return location;
    }

    @Override
    public String getSensorType() {
        return type;
    }

    /**
     * Generates sensor event
     * @param event
     * @return event
     */
    @Override
    public String generateSensorEvent(Event event) {
        return this.sensorName + " detected message " + event.getMessage();
    }

    @Override
    public String toString() {
        return "Microphone{" +
                "sensorId='" + sensorId + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", location=" + location +
                ", type='" + type + '\'' +
                '}';
    }
}
