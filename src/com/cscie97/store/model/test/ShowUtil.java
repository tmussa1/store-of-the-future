package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A utility class to show details for get requests
 * @author Tofik Mussa
 */
public class ShowUtil {

    /**
     * Shows details of store
     * @param storeModelService
     * @param storeId
     * @return details of a store
     * @throws StoreException
     */
    public static String showStoreDetails(IStoreModelService storeModelService, String storeId) throws StoreException {
        Store store = storeModelService.getStoreById(storeId);
        return DetailsUtil.outputDetails("Store " , store.getStoreName(), store.getAddress().getCity(),
                store.getAisles());
    }

    /**
     * Shows details of aisle
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @return details of aisle
     * @throws StoreException
     */
    public static String showAisleDetails(IStoreModelService storeModelService, String storeId,
                                          String aisleNumber) throws StoreException {
        Aisle aisle = storeModelService.getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        return DetailsUtil.outputDetails("Aisle ", aisle.getAisleDescription(),
                aisle.getLocation().name(),aisle.getShelves());
    }

    /**
     * Shows details of a shelf
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @return details of a shelf
     * @throws StoreException
     */
    public static String showShelfDetails(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                          String shelfId) throws StoreException {
        Shelf shelf = storeModelService.getShelfByStoreIdAisleNumShelfId(storeId, aisleNumber, shelfId );
        return DetailsUtil.outputDetails("Shelf ", shelf.getShelfName(), aisleNumber, shelf.getInventoryList());
    }

    /**
     * Shows details of a product
     * @param storeModelService
     * @param productId
     * @return details of a product
     * @throws StoreException
     */
    public static String showProductDetails(IStoreModelService storeModelService, String productId)
            throws StoreException {
        Product product = storeModelService.getProductById(productId);
        return DetailsUtil.outputDetails("Product " , product.getProductName(),
                product.getCategory(), List.of(product));
    }

    /**
     * Shows details of inventory
     * @param storeModelService
     * @param inventoryId
     * @return inventory details
     * @throws StoreException
     */
    public static String showInventoryDetails(IStoreModelService storeModelService, String inventoryId)
            throws StoreException {
        Inventory inventory = storeModelService.getInventoryById(inventoryId);

        return DetailsUtil.outputDetails("Inventory ", String.valueOf(inventory.getInventoryId()),
                inventory.getInventoryLocation().getAisleNumber(), List.of(inventory.getProduct()));
    }

    /**
     * Shows customer details
     * @param storeModelService
     * @param customerId
     * @return customer details
     * @throws StoreException
     */
    public static String showCustomerDetails(IStoreModelService storeModelService, String customerId)
            throws StoreException {
        Customer customer = storeModelService.getCustomerById(customerId);
        return DetailsUtil.outputDetails("Customer ", customer.getFirstName(), customer.getCustomerLocation().getAisleNumber(),
                List.of(customer.getEmailAddress()));
    }

    /**
     * Shows basket of a customer
     * @param storeModelService
     * @param customerId
     * @return basket details
     * @throws StoreException
     */
    public static String showBasketOfACustomer(IStoreModelService storeModelService, String customerId)
            throws StoreException {
        Basket basketOfACustomer = storeModelService.getBasketOfACustomer(customerId);
        return DetailsUtil.outputDetails("Basket ", basketOfACustomer.getBasketId(), customerId,
                List.copyOf(basketOfACustomer.getProductsMap().keySet()));
    }

    /**
     * Shows items in a basket
     * @param storeModelService
     * @param basketId
     * @return details of items in a basket
     * @throws StoreException
     */
    public static String showBasketItems(IStoreModelService storeModelService, String basketId)
            throws StoreException {
        Map<Product, Integer> basketItems = storeModelService.getBasketItems(basketId);

        if(basketItems.size() == 0){
            return DetailsUtil.outputDetails("Basket is empty ", basketId,
                    "The basket is not associated with any customer", Collections.emptyList());
        }
        return DetailsUtil.outputDetails("Basket ", basketId, " contains items ",
                List.copyOf(basketItems.keySet()));
    }

    /**
     * Shows details of a sensor
     * @param storeModelService
     * @param storeId
     * @param aisleNumber
     * @param sensorId
     * @return details of a sensor
     * @throws StoreException
     */
    public static String showSensor(IStoreModelService storeModelService, String storeId, String aisleNumber,
                             String sensorId) throws StoreException {
        ISensor sensor = storeModelService.getSensorByLocationAndSensorId(storeId, aisleNumber, sensorId);
        return DetailsUtil.outputDetails("Sensor ", sensor.getSensorName(), " contains items ",
                List.of(sensor));
    }

    /**
     * Shows the details of an appliance
     * @param storeModelService
     * @param applianceId
     * @param storeId
     * @param aisleNumber
     * @return details of an appliance
     * @throws StoreException
     */
    public static String showAppliance(IStoreModelService storeModelService, String applianceId, String storeId,
                                       String aisleNumber) throws StoreException {
        IAppliance appliance = storeModelService.getApplianceByLocationAndSensorId(storeId, aisleNumber, applianceId);
        return DetailsUtil.outputDetails("Applaince ", appliance.getApplianceName(),
                " contains items ", List.of(appliance));
    }

}
