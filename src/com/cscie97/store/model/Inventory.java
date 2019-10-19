package com.cscie97.store.model;

/**
 * An association class to keep the count and capacity of products
 * @author Tofik Mussa
 */
public class Inventory {

    private String inventoryId;
    private Product product;
    private int count;
    private int capacity;
    private InventoryLocation inventoryLocation;

    /**
     *
     * @param inventoryId
     * @param product
     * @param count
     * @param capacity
     * @param inventoryLocation
     */
    public Inventory(String inventoryId, Product product, int count, int capacity, InventoryLocation inventoryLocation) {
        this.inventoryId = inventoryId;
        this.product = product;
        this.count = count;
        this.capacity = capacity;
        this.inventoryLocation = inventoryLocation;
    }

    /**
     * Updates inventory count
     * @param count
     * @throws StoreException
     */
    public void setCount(int count) throws StoreException {
        if(isValidCount(count)){
            this.count = count;
        } else {
            throw new StoreException("Invalid inventory count");
        }

    }

    /**
     * Validates if count is between 0 and maximum capacity
     * @param count
     * @return
     */
    private boolean isValidCount(int count) {
        return count >= 0 && count <= getCapacity();
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }

    public InventoryLocation getInventoryLocation() {
        return inventoryLocation;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId='" + inventoryId + '\'' +
                ", product=" + product +
                ", count=" + count +
                ", capacity=" + capacity +
                ", inventoryLocation=" + inventoryLocation +
                '}';
    }
}
