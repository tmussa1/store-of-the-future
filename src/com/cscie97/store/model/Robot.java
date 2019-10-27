package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Robot implements ISensor, IAppliance {

    private String sensorId;
    private String sensorName;
    private InventoryLocation sensorLocation;
    private String sensorType;

    /**
     *
     * @param sensorId
     * @param sensorName
     * @param sensorLocation
     * @param sensorType
     */
    public Robot(String sensorId, String sensorName, InventoryLocation sensorLocation, String sensorType) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.sensorLocation = sensorLocation;
        this.sensorType = sensorType;
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
        return getSensorLocation();
    }

    @Override
    public String getSensorType() {
        return getSensorType();
    }

    @Override
    public String generateSensorEvent(Event event) {
        return this.sensorName + " detected message " + event.getMessage();
    }

    @Override
    public String getApplianceId() {
        return getSensorId();
    }

    @Override
    public String getApplianceName() {
        return getSensorName();
    }

    @Override
    public InventoryLocation getApplianceLocation() {
        return getSensorLocation();
    }

    @Override
    public String getApplianceType() {
        return getSensorType();
    }

    /**
     * Moves a robot
     * @param sensorLocation
     */
    public void setSensorLocation(InventoryLocation sensorLocation) {
        this.sensorLocation = sensorLocation;
    }

    public void setApplianceLocation(InventoryLocation applianceLocation){
        this.sensorLocation = applianceLocation;
        setSensorLocation(applianceLocation);
    }

    /**
     * Generates appliance event
     * @param event
     * @return event
     */
    @Override
    public String generateApplianceEvent(Event event) {
        return this.getApplianceName() + " detected message " + event.getMessage();
    }

    /**
     * Generates a sensor event
     * @param command
     * @return event
     */
    @Override
    public String listenToCommand(Command command) {
        return this.getApplianceName() + " is doing " + command.getMessage();
    }

    @Override
    public String toString() {
        return "Robot{" +
                "sensorId='" + sensorId + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", sensorLocation=" + sensorLocation +
                ", sensorType='" + sensorType + '\'' +
                '}';
    }
}
