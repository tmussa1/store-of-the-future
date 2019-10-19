package com.cscie97.store.model;

/**
 * Sensors are microphones, robots and cameras
 * @author Tofik Mussa
 */
public interface ISensor {

    String getSensorId();

    String getSensorName();

    InventoryLocation getSensorLocation();

    String getSensorType();

    String generateSensorEvent(Event event);
}
