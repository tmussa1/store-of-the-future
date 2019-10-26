package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Turnstile implements IAppliance {

    private String applianceId;
    private String applianceName;
    private InventoryLocation applianceLocation;
    private String applianceType;
    private boolean isOpen;

    /**
     *
     * @param applianceId
     * @param applianceName
     * @param applianceLocation
     */
    public Turnstile(String applianceId, String applianceName, InventoryLocation applianceLocation) {
        this.applianceId = applianceId;
        this.applianceName = applianceName;
        this.applianceLocation = applianceLocation;
        this.isOpen = false;
        this.applianceType = this.getClass().getName();
    }

    public Turnstile openTurnstile(){
        this.isOpen = true;
        return this;
    }

    public Turnstile closeTurnstile(){
        this.isOpen = false;
        return this;
    }

    @Override
    public String getApplianceId() {
        return applianceId;
    }

    @Override
    public String getApplianceName() {
        return applianceName;
    }

    @Override
    public InventoryLocation getApplianceLocation() {
        return applianceLocation;
    }

    @Override
    public String getApplianceType() {
        return applianceType;
    }

    /**
     * Generates appliance event
     * @param event
     * @return event
     */
    @Override
    public String generateApplianceEvent(Event event) {
        return this.applianceName + " detected message " + event.getMessage();
    }

    /**
     * Listens to appliance command
     * @param command
     * @return command
     */
    @Override
    public String listenToCommand(Command command) {
        return this.applianceName + " is doing " + command.getMessage();
    }

    @Override
    public String toString() {
        return "Turnstile{" +
                "applianceId='" + applianceId + '\'' +
                ", applianceName='" + applianceName + '\'' +
                ", applianceLocation=" + applianceLocation +
                ", applianceType='" + applianceType + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
