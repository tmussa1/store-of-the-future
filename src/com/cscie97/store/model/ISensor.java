package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public interface ISensor {

    String getSensorId();

    String getSensorName();

    InventoryLocation getSensorLocation();

    String getSensorType();

    String generateSensorEvent(Event event);
}
