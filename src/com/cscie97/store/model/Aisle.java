package com.cscie97.store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Aisle {

    private String aisleNumber;
    private String aisleDescription;
    private LocationType location;
    private List<Shelf> shelves;
    private List<ISensor> sensors;
    private List<IAppliance> appliances;

    public Aisle(String aisleNumber, String aisleDescription, LocationType location) {
        this.aisleNumber = aisleNumber;
        this.aisleDescription = aisleDescription;
        this.location = location;
        this.shelves = new ArrayList<>();
        this.sensors = new ArrayList<>();
        this.appliances = new ArrayList<>();
    }

    public void addShelfToAisle(Shelf shelf){
        this.shelves.add(shelf);
    }

    public void addSensorToShelf(ISensor sensor){
        this.sensors.add(sensor);
    }

    public void addApplianceToShelf(IAppliance appliance){
        this.appliances.add(appliance);
    }

    public ISensor getSensorById(String sensorId){
        return this.sensors.stream()
                .filter(sensor -> sensor.getSensorId().equals(sensorId))
                .findAny().get();
    }

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

    @Override
    public String toString() {
        return "Aisle{" +
                "aisleNumber='" + aisleNumber + '\'' +
                ", aisleDescription='" + aisleDescription + '\'' +
                ", location=" + location +
                '}';
    }
}
