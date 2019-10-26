package com.cscie97.store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A store consists of 1 to many aisles
 * @author Tofik Mussa
 */
public class Aisle {

    private String aisleNumber;
    private String aisleDescription;
    private LocationType location;
    private List<Shelf> shelves;
    private List<ISensor> sensors;
    private List<IAppliance> appliances;

    /**
     *
     * @param aisleNumber
     * @param aisleDescription
     * @param location
     */
    public Aisle(String aisleNumber, String aisleDescription, LocationType location) {
        this.aisleNumber = aisleNumber;
        this.aisleDescription = aisleDescription;
        this.location = location;
        this.shelves = new ArrayList<>();
        this.sensors = new ArrayList<>();
        this.appliances = new ArrayList<>();
    }

    /**
     * Adds shelf to aisle
     * @param shelf
     */
    public void addShelfToAisle(Shelf shelf){
        this.shelves.add(shelf);
    }

    /**
     * Adds a sensor to aisle
     * @param sensor
     */
    public void addSensorToAisle(ISensor sensor){
        this.sensors.add(sensor);
    }

    /**
     * Adds an appliance to aisle
     * @param appliance
     */
    public void addApplianceToAisle(IAppliance appliance){
        this.appliances.add(appliance);
    }

    /**
     * Finds a sensor by sensor id
     * @param sensorId
     * @return
     */
    public ISensor getSensorById(String sensorId){
        return this.sensors.stream()
                .filter(sensor -> sensor.getSensorId().equals(sensorId))
                .findAny().get();
    }

    /**
     * Finds an appliance by appliance id
     * @param applianceId
     * @return
     * @throws StoreException
     */
    public IAppliance getApplianceById(String applianceId) throws StoreException {
        Optional<IAppliance> appliance = this.appliances.stream()
                .filter(applian -> applian.getApplianceId().equals(applianceId))
                .findAny();
        if(appliance.isEmpty()){
            throw new StoreException("Appliance with requested id doesn't exist");
        }
        return appliance.get();
    }

    public String getAisleNumber() {
        return aisleNumber;
    }

    public String getAisleDescription() {
        return aisleDescription;
    }

    public LocationType getLocation() {
        return location;
    }

    public List<Shelf> getShelves() {
        return shelves;
    }

    public List<ISensor> getSensors() {
        return sensors;
    }

    public List<IAppliance> getAppliances() {
        return appliances;
    }

    @Override
    public String toString() {
        return "Aisle{" +
                "aisleNumber='" + aisleNumber + '\'' +
                ", aisleDescription='" + aisleDescription + '\'' +
                ", location=" + location +
                '}';
    }
}
