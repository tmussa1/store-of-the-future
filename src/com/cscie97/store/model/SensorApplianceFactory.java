package com.cscie97.store.model;

/**
 * A utility factory class to instantiate sensor and appliance concrete objects
 * @author Tofik Mussa
 */
public class SensorApplianceFactory {

    /**
     * Creates a sensor object
     * @param type
     * @param sensorId
     * @param sensorName
     * @param location
     * @return a sensor object
     * @throws StoreException
     */
    public static ISensor createSensor(String type, String sensorId, String sensorName, InventoryLocation location)
            throws StoreException {
        switch(type.toLowerCase()){
            case "microphone":
                return new Microphone(sensorId, sensorName, location);
            case "camera":
                return new Camera(sensorId, sensorName, location);
            default:
                throw new StoreException("The sensor that can be created is not one of : camera, microphone");
        }
    }

    /**
     * Creates an appliance object
     * @param type
     * @param applianceId
     * @param applianceName
     * @param location
     * @return an appliance object
     * @throws StoreException
     */
    public static IAppliance createAppliance(String type, String applianceId, String applianceName,
                                             InventoryLocation location) throws StoreException {
        switch(type.toLowerCase()){
            case "robot":
                return new Robot(applianceId, applianceName, location, type);
            case "turnstile":
                return new Turnstile(applianceId, applianceName, location);
            case "speaker":
                return new Speaker(applianceId, applianceName, location);
            default:
                throw new StoreException("The appliance that can be created is not one of : robot, speaker, turnstile");
        }
    }
}
