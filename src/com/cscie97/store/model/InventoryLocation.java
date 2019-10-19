package com.cscie97.store.model;

/**
 * A convenience class to identify the location of entities. It is used for more than just inventories
 * @author Tofik Mussa
 */
public class InventoryLocation {

    private String storeId;
    private String aisleNumber;
    private String shelfId;

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     */
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

    @Override
    public String toString() {
        return "InventoryLocation{" +
                "storeId='" + storeId + '\'' +
                ", aisleNumber='" + aisleNumber + '\'' +
                ", shelfId='" + shelfId + '\'' +
                '}';
    }
}
