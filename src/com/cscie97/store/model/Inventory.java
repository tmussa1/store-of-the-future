package com.cscie97.store.model;

public class Inventory {

    private String inventoryId;
    private Product product;
    private int count;
    private int capacity;
    private InventoryLocation inventoryLocation;

    public Inventory(String inventoryId, Product product, int count, int capacity, InventoryLocation inventoryLocation) {
        this.inventoryId = inventoryId;
        this.product = product;
        this.count = count;
        this.capacity = capacity;
        this.inventoryLocation = inventoryLocation;
    }

    public void setCount(int count) throws StoreException {
        if(isValidCount(count)){
            this.count = count;
        } else {
            throw new StoreException("Invalid inventory count");
        }

    }

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
}
