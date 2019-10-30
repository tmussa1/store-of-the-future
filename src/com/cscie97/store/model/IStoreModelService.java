package com.cscie97.store.model;

import java.util.List;
import java.util.Map;

/**
 * This is the Store Model Service Interface which is the only class the outside world knows about
 * @author Tofik Mussa
 */
public interface IStoreModelService {

    Store createAStore(String storeId, String storeName, Address storeAddress) throws StoreException;

    Store getStoreById(String storeId) throws StoreException;

    Aisle createAisle(String storeId, String aisleNumber, String aisleDescription, String location) throws StoreException;

    Aisle getAisleByStoreIdAndAisleNumber(String storeId, String aisleNumber) throws StoreException;

    Shelf createAShelf(String storeId, String aisleNumber, String shelfId, String shelfName, String level, String shelfDescription, String temperature) throws StoreException;

    Shelf getShelfByStoreIdAisleNumShelfId(String storeId, String aisleNumber, String shelfId) throws StoreException;

    Inventory createInventory(String inventoryId, String storeId, String aisleNumber, String shelfId, int capacity, int count, String productId) throws StoreException;

    Inventory getInventoryById(String inventoryId) throws StoreException;

    int updateInventoryCount(String inventoryId, int difference) throws StoreException;

    Product createAProduct(String productId, String productName, String productDescription, int size, String category, int price, String temperature);

    Product getProductById(String productId) throws StoreException;

    Customer createCustomer(String customerId, String firstName, String lastName, String type, String emailAddress, String accountAddress) throws StoreException;

    Customer getCustomerById(String customerId) throws StoreException;

    InventoryLocation updateCustomerLocation(String customerId, String storeId, String aisleNumber) throws StoreException;

    Basket getBasketOfACustomer(String customerId) throws StoreException;

    Basket createBasketForACustomer(String customerId, String basketId) throws StoreException;

    Basket addItemToBasket(String basketId, String productId, int count) throws StoreException;

    Inventory getInventoryByProductId(String productId) throws StoreException;

    Basket removeItemFromBasket(String basketId, String productId, int countReturned) throws StoreException;

    Customer clearBasketAndRemoveAssociationWithACustomer(String basketId) throws StoreException;

    Map<Product, Integer> getBasketItems(String basketId) throws StoreException;

    ISensor createASensor(String sensorId, String sensorName, String sensorType, String storeId, String aisleNumber) throws StoreException;

    ISensor getSensorByLocationAndSensorId(String storeId, String aisleNumber, String sensorId) throws StoreException;

    IAppliance getApplianceByLocationAndSensorId(String storeId, String aisleNumber, String applianceId) throws StoreException;

    String createSensorEvent(String storeId, String aisleNumber, String sensorId, Event event) throws StoreException;

    IAppliance createAnAppliance(String applianceId, String applianceName, String applianceType, String storeId, String aisleNumber) throws StoreException;

    String createApplianceEvent(String storeId, String aisleNumber, String applianceId, Event event) throws StoreException;

    String createApplianceCommand(String storeId, String aisleNumber, String applianceId, Command command) throws StoreException;

    IAppliance moveRobot(String storeId, String aisleNumber, String applianceRobotId, String newAisleNumber) throws StoreException;

    List<Turnstile> getAllTurnstilesWithinAnAisle(String storeId, String aisleNumber) throws StoreException;

    List<Turnstile> openTurnstiles(List<Turnstile> turnstiles);

    List<Turnstile> closeTurnstiles(List<Turnstile> turnstiles);

    List<Speaker> getAllSpeakersWithinAnAisle(String storeId, String aisleNumber) throws StoreException;

    List<Robot> getAllRobotsWithinAnAisle(String storeId, String aisleNumber) throws StoreException;

    Customer getCustomerByCustomerName(String customerName) throws StoreException;

    Event createAnEvent(Event event);

    List<IObserver> getObservers();

    void setAuthKey(String authKey);
}

