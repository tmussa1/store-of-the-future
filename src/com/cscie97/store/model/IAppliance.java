package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public interface IAppliance {

    String getApplianceId();

    String getApplianceName();

    InventoryLocation getApplianceLocation();

    String getApplianceType();

    String generateApplianceEvent(Event event);

    String listenToCommand(Command command);
}
