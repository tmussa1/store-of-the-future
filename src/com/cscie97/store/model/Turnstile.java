package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Turnstile implements IAppliance {

    private String applianceId;
    private String applianceName;
    private InventoryLocation applianceLocation;
    private String applianceType;

    public Turnstile(String applianceId, String applianceName, InventoryLocation applianceLocation) {
        this.applianceId = applianceId;
        this.applianceName = applianceName;
        this.applianceLocation = applianceLocation;
        this.applianceType = this.getClass().getName();
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

    @Override
    public String generateApplianceEvent(Event event) {
        return this.applianceName + " detected message " + event.getMessage();
    }

    @Override
    public String listenToCommand(Command command) {
        return this.applianceName + " is doing " + command.getMessage();
    }


}
