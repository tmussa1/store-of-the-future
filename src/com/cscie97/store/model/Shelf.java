package com.cscie97.store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Tofik Mussa
 */
public class Shelf {
    private String shelfId;
    private String shelfName;
    private Level level;
    private String shelfDescription;
    private Temperature temperature;
    private List<Inventory> inventoryList;

    /**
     *
     * @param shelfId
     * @param shelfName
     * @param level
     * @param shelfDescription
     * @param temperature
     */
    public Shelf(String shelfId, String shelfName, Level level, String shelfDescription, Temperature temperature) {
        this.shelfId = shelfId;
        this.shelfName = shelfName;
        this.level = level;
        this.shelfDescription = shelfDescription;
        this.temperature = temperature;
        this.inventoryList = new ArrayList<>();
    }

    /**
     * Adds inventory to the shelf
     * @param inventory
     * @throws StoreException
     */
    public void addInventoryToShelf(Inventory inventory) throws StoreException {
        Inventory inventoryExisting = getInventoryInTheShelfByInventoryId(inventory.getInventoryId());
        if(inventoryExisting != null){
            this.inventoryList.set(inventoryList.indexOf(inventoryExisting), inventory);
        } else {
            this.inventoryList.add(inventory);
        }
    }

    /**
     * Gets inventory location by inventory id
     * @param inventoryId
     * @return
     * @throws StoreException
     */
    public Inventory getInventoryInTheShelfByInventoryId(String inventoryId) throws StoreException {
        Optional<Inventory> inventory = this.inventoryList.stream()
                .filter(anInventory -> anInventory.getInventoryId().equals(inventoryId))
                .findAny();
        if(!inventory.isEmpty()){
            return inventory.get();
        }
        return null;
    }

    public String getShelfId() {
        return shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public Level getLevel() {
        return level;
    }

    public String getShelfDescription() {
        return shelfDescription;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "shelfId='" + shelfId + '\'' +
                ", shelfName='" + shelfName + '\'' +
                ", level=" + level +
                ", shelfDescription='" + shelfDescription + '\'' +
                ", temperature=" + temperature +
                ", inventoryList=" + inventoryList +
                '}';
    }
}