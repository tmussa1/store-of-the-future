package com.cscie97.store.model;



/**
 * @author Tofik Mussa
 */
public class StoreUtil {

    /**
     * We can assume that the aisles are by default in the floor unless specified
     * @param location
     * @return
     */
    public static LocationType convertLocationToEnum(String location) {
        LocationType locationEnum = null;
        switch(location.toUpperCase()){
            case "FLOOR":
                locationEnum =  LocationType.FLOOR;
                break;
            case "STOREROOM":
                locationEnum = LocationType.STORE_ROOM;
                break;
            default:
                locationEnum =  LocationType.FLOOR;
        }
        return locationEnum;
    }

    /**
     *
     * @param level
     * @return
     */
    public static Level convertLevelToEnum(String level){
        Level levelEnum = null;
        switch(level.toUpperCase()){
            case "HIGH":
                levelEnum = Level.HIGH;
                break;
            case "MEDIUM":
                levelEnum = Level.MEDIUM;
                break;
            case "LOW":
                levelEnum = Level.LOW;
                break;
            default:
                levelEnum = Level.MEDIUM;
        }
        return levelEnum;
    }

    /**
     * Temperature is by default ambient
     * @param temperature
     * @return
     */

    public static Temperature convertTemperatureToEnum(String temperature){
        Temperature temperatureEnum = null;
        switch(temperature.toUpperCase()){
            case "FROZEN":
                temperatureEnum = Temperature.FROZEN;
                break;
            case"REFRIGERATED":
                temperatureEnum = Temperature.REFRIGERATED;
                break;
            case "AMBIENT":
                temperatureEnum = Temperature.AMBIENT;
                break;
            case "WARM":
                temperatureEnum = Temperature.WARM;
                break;
            case "HOT":
                temperatureEnum = Temperature.HOT;
                break;
            default:
                temperatureEnum = Temperature.AMBIENT;
        }
        return temperatureEnum;
    }

    /**
     * Customer that is not registered is by default a guest
     * @param customerType
     * @return
     */
    public static CustomerType convertCustomerTypeToEnum(String customerType){
        CustomerType customerEnum = null;
        switch(customerType.toUpperCase()){
            case "REGISTERED":
                customerEnum = CustomerType.REGISTERED;
                break;
            case "GUEST":
                customerEnum = CustomerType.GUEST;
                break;
            default:
                customerEnum = CustomerType.GUEST;
                break;
        }
        return customerEnum;
    }
}
