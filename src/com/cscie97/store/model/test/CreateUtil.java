package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

/**
 * A utility class to run create commands
 * @author Tofik Mussa
 */
public class CreateUtil {

    /**
     * Creates a new store
     * @param storeModelService
     * @param storeId
     * @param storeName
     * @param street
     * @param city
     * @param state
     * @return store creation confirmation
     * @throws StoreException
     */
    public static String createStore(IStoreModelService storeModelService, String storeId, String storeName,
                                     String street, String city, String state) throws StoreException {
        Address address = new Address(street, city, state);
        Store store = storeModelService.createAStore(storeId, storeName, address);
        return DetailsUtil.outputConfirmation(store.getStoreName());
    }

    /**
     * Creates a new aisle
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param aisleDescription
     * @param location
     * @return confirmation of aisle creation
     * @throws StoreException
     */
    public static String createAisle(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                     String aisleDescription, String location) throws StoreException {
        Aisle aisle = storeModelService.createAisle(storeId,aisleNumber, aisleDescription, location);
        return DetailsUtil.outputConfirmation(aisle.getAisleDescription());
    }

    /**
     * Creates a new shelf
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param shelfName
     * @param level
     * @param shelfDescription
     * @param temperature
     * @return confirmation of shelf creation
     * @throws StoreException
     */
    public static String createShelf(IStoreModelService storeModelService,String storeId, String aisleNumber,
                                     String shelfId, String shelfName, String level, String shelfDescription,
                                     String temperature) throws StoreException {
        Shelf shelf = storeModelService.createAShelf(storeId, aisleNumber, shelfId,
                shelfName, level, shelfDescription, temperature);
        return DetailsUtil.outputConfirmation(shelf.getShelfName());
    }

    /**
     * Creates a new product
     * @param storeModelService
     * @param productId
     * @param productName
     * @param productDescription
     * @param size
     * @param category
     * @param price
     * @param temperature
     * @return confirmation of product creation
     */
    public static String createProduct(IStoreModelService storeModelService, String productId, String productName,
                                       String productDescription, String size, String category, String price,
                                       String temperature){
        Product product = storeModelService.createAProduct(productId, productName, productDescription,
                convertToInteger(size), category, convertToInteger(price), temperature);
        return DetailsUtil.outputConfirmation(product.getProductName());
    }

    /**
     * Creates a new inventory
     * @param storeModelService
     * @param inventoryId
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param capacity
     * @param count
     * @param productId
     * @return confirmation of inventory creation
     * @throws StoreException
     */
    public static String createInventory(IStoreModelService storeModelService, String inventoryId, String storeId,
                                         String aisleNumber, String shelfId, String capacity,
                                         String count, String productId) throws StoreException {
        Inventory inventory = storeModelService.createInventory(inventoryId, storeId, aisleNumber,
                shelfId, convertToInteger(capacity), convertToInteger(count), productId);
        return DetailsUtil.outputConfirmation(String.valueOf(inventory.getInventoryId()));
    }

    /**
     * Creates a new customer
     * @param storeModelService
     * @param customerId
     * @param firstName
     * @param lastName
     * @param type
     * @param emailAddress
     * @param accountAddress
     * @return confirmation of customer creation
     * @throws StoreException
     */
    public static String createCustomer(IStoreModelService storeModelService, String customerId, String firstName,
                                        String lastName, String type, String emailAddress, String accountAddress)
            throws StoreException {
        Customer customer = storeModelService.createCustomer(customerId, firstName, lastName, type,
                emailAddress, accountAddress);
        return DetailsUtil.outputConfirmation(customer.getFirstName());
    }

    /**
     * Creates basket for a customer
     * @param storeModelService
     * @param customerId
     * @param basketId
     * @return confirmation of basket creation
     * @throws StoreException
     */
    public static String createBasketForACustomer(IStoreModelService storeModelService, String customerId,
                                                  String basketId) throws StoreException {
        Basket basketForACustomer = storeModelService.createBasketForACustomer(customerId, basketId);
        return DetailsUtil.outputConfirmation(basketForACustomer.getBasketId());
    }

    /**
     * Creates a sensor object
     * @param storeModelService
     * @param sensorId
     * @param sensorName
     * @param sensorType
     * @param storeId
     * @param aisleNumber
     * @return confirmation of sensor creation
     * @throws StoreException
     */
    public static String createSensor(IStoreModelService storeModelService, String sensorId, String sensorName,
                                      String sensorType, String storeId, String aisleNumber) throws StoreException {
        ISensor sensor = storeModelService.createASensor(sensorId, sensorName, sensorType, storeId, aisleNumber);
        return DetailsUtil.outputConfirmation(sensor.getSensorName());
    }

    /**
     * Creates a sensor event
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param sensorId
     * @param command
     * @return confirmation of sensor event creation
     * @throws StoreException
     */
    public static String createSensorEvent(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                           String sensorId, String command) throws StoreException {
        Event event = new Event(command);
        String eventReturned = storeModelService.createSensorEvent(storeId, aisleNumber, sensorId, event);
        return DetailsUtil.outputConfirmation(eventReturned);
    }

    /**
     * Creates an appliance
     * @param storeModelService
     * @param applianceId
     * @param applianceName
     * @param applianceType
     * @param storeId
     * @param aisleNumber
     * @return confirmation of appliance creation
     * @throws StoreException
     */
    public static String createAnAppliance(IStoreModelService storeModelService, String applianceId,
                                           String applianceName, String applianceType, String storeId,
                                           String aisleNumber) throws StoreException {
        IAppliance appliance = storeModelService.createAnAppliance(applianceId, applianceName,
                applianceType, storeId, aisleNumber);
        return DetailsUtil.outputConfirmation(appliance.getApplianceName());
    }

    /**
     * Creates an appliance event
     * @param storeModelService
     * @param applianceId
     * @param message
     * @param storeId
     * @param aisleNumber
     * @return confirmation of appliance event creation
     * @throws StoreException
     */
    public static String createApplianceEvent(IStoreModelService storeModelService,
                                              String applianceId, String message, String storeId,
                                              String aisleNumber) throws StoreException {
        Event event = new Event(message);
        String applianceEvent = storeModelService.createApplianceEvent(storeId, aisleNumber, applianceId, event);
        return DetailsUtil.outputConfirmation(applianceEvent);
    }

    /**
     * Creates an appliance command
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param applianceId
     * @param message
     * @return confirmation of appliance command creation
     * @throws StoreException
     */
    public static String createApplianceCommand(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                         String applianceId, String message) throws StoreException {
        Command command = new Command(message);
        String applianceCommand = storeModelService.createApplianceCommand(storeId, aisleNumber, applianceId, command);
        return DetailsUtil.outputConfirmation(applianceCommand);
    }

    /**
     *
     * @param storeModelService
     * @param event
     * @return event creation confirmation
     */
    public static String createEventSCSListensTo(IStoreModelService storeModelService, Event event){
        Event anEvent = storeModelService.createAnEvent(event);
        return "An event " + anEvent.getMessage() + " received!!!!!!!!!!!!!!!!!!!!!!!!!!!";
    }

    /**
     * Adds an observer to SMS
     * @param storeModelService
     * @param observer
     * @return adds new observer
     */
    public static String createAnObserver(IStoreModelService storeModelService, IObserver observer){
        storeModelService.getObservers().add(observer);
        return DetailsUtil.outputConfirmation(observer.toString());
    }

    /**
     * Utility methos to convert String to int
     * @param str
     * @return an integer
     */
    public static int convertToInteger(String str){
        return Integer.parseInt(str);
    }




}
