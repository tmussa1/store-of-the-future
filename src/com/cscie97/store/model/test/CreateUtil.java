package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

/**
 * @author Tofik Mussa
 */
public class CreateUtil {


    public static String createStore(IStoreModelService storeModelService, String storeId, String storeName,
                                     String street, String city, String state) throws StoreException {
        Address address = new Address(street, city, state);
        Store store = storeModelService.createAStore(storeId, storeName, address);
        return DetailsUtil.outputConfirmation(store.getStoreName());
    }

    public static String createAisle(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                     String aisleDescription, String location) throws StoreException {
        Aisle aisle = storeModelService.createAisle(storeId,aisleNumber, aisleDescription, location);
        return DetailsUtil.outputConfirmation(aisle.getAisleDescription());
    }

    public static String createShelf(IStoreModelService storeModelService,String storeId, String aisleNumber,
                                     String shelfId, String shelfName, String level, String shelfDescription,
                                     String temperature) throws StoreException {
        Shelf shelf = storeModelService.createAShelf(storeId, aisleNumber, shelfId,
                shelfName, level, shelfDescription, temperature);
        return DetailsUtil.outputConfirmation(shelf.getShelfName());
    }

    public static String createProduct(IStoreModelService storeModelService, String productId, String productName,
                                       String productDescription, String size, String category, String price,
                                       String temperature){
        Product product = storeModelService.createAProduct(productId, productName, productDescription,
                convertToInteger(size), category, convertToInteger(price), temperature);
        return DetailsUtil.outputConfirmation(product.getProductName());
    }


    public static String createInventory(IStoreModelService storeModelService, String inventoryId, String storeId,
                                         String aisleNumber, String shelfId, String capacity,
                                         String count, String productId) throws StoreException {
        Inventory inventory = storeModelService.createInventory(inventoryId, storeId, aisleNumber,
                shelfId, convertToInteger(capacity), convertToInteger(count), productId);
        return DetailsUtil.outputConfirmation(String.valueOf(inventory.getInventoryId()));
    }

    public static String createCustomer(IStoreModelService storeModelService, String customerId, String firstName,
                                        String lastName, String type, String emailAddress, String accountAddress)
            throws StoreException {
        Customer customer = storeModelService.createCustomer(customerId, firstName, lastName, type,
                emailAddress, accountAddress);
        return DetailsUtil.outputConfirmation(customer.getFirstName());
    }

    public static String createBasketForACustomer(IStoreModelService storeModelService, String customerId,
                                                  String basketId) throws StoreException {
        Basket basketForACustomer = storeModelService.createBasketForACustomer(customerId, basketId);
        return DetailsUtil.outputConfirmation(basketForACustomer.getBasketId());
    }

    public static String createSensor(IStoreModelService storeModelService, String sensorId, String sensorName,
                                      String sensorType, String storeId, String aisleNumber) throws StoreException {
        ISensor sensor = storeModelService.createASensor(sensorId, sensorName, sensorType, storeId, aisleNumber);
        return DetailsUtil.outputConfirmation(sensor.getSensorName());
    }

    public static String createSensorEvent(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                           String sensorId, String command) throws StoreException {
        Event event = new Event(command);
        String eventReturned = storeModelService.createSensorEvent(storeId, aisleNumber, sensorId, event);
        return DetailsUtil.outputConfirmation(eventReturned);
    }

    public static String createAnAppliance(IStoreModelService storeModelService, String applianceId,
                                           String applianceName, String applianceType, String storeId,
                                           String aisleNumber) throws StoreException {
        IAppliance appliance = storeModelService.createAnAppliance(applianceId, applianceName,
                applianceType, storeId, aisleNumber);
        return DetailsUtil.outputConfirmation(appliance.getApplianceName());
    }

    public static String createApplianceEvent(IStoreModelService storeModelService,
                                              String applianceId, String message, String storeId,
                                              String aisleNumber) throws StoreException {
        Event event = new Event(message);
        String applianceEvent = storeModelService.createApplianceEvent(storeId, aisleNumber, applianceId, event);
        return DetailsUtil.outputConfirmation(applianceEvent);
    }

    public static String createApplianceCommand(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                         String applianceId, String message) throws StoreException {
        Command command = new Command(message);
        String applianceCommand = storeModelService.createApplianceCommand(storeId, aisleNumber, applianceId, command);
        return DetailsUtil.outputConfirmation(applianceCommand);
    }

    public static int convertToInteger(String str){
        return Integer.parseInt(str);
    }




}
