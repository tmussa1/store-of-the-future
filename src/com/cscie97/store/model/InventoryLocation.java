package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class InventoryLocation {

    private String storeId;
    private String aisleNumber;
    private String shelfId;

    public InventoryLocation(String storeId, String aisleNumber, String shelfId) {
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.shelfId = shelfId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getAisleNumber() {
        return aisleNumber;
    }

    public String getShelfId() {
        return shelfId;
    }
}
