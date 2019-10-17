package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ShowUtil {

    public static String showStoreDetails(IStoreModelService storeModelService, String storeId) throws StoreException {
        Store store = storeModelService.getStoreById(storeId);
        return DetailsUtil.outputDetails("Store " , store.getStoreName(), store.getAddress().getCity(),
                store.getAisles());
    }

    public static String showAisleDetails(IStoreModelService storeModelService, String storeId,
                                          String aisleNumber) throws StoreException {
        Aisle aisle = storeModelService.getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        return DetailsUtil.outputDetails("Aisle ", aisle.getAisleDescription(),
                aisle.getLocation().name(),aisle.getShelves());
    }

    public static String showShelfDetails(IStoreModelService storeModelService, String storeId, String aisleNumber,
                                          String shelfId) throws StoreException {
        Shelf shelf = storeModelService.getShelfByStoreIdAisleNumShelfId(storeId, aisleNumber, shelfId );
        return DetailsUtil.outputDetails("Shelf ", shelf.getShelfName(), aisleNumber, shelf.getInventoryList());
    }

    public static String showProductDetails(IStoreModelService storeModelService, String productId)
            throws StoreException {
        Product product = storeModelService.getProductById(productId);
        return DetailsUtil.outputDetails("Product " , product.getProductName(),
                product.getCategory(), List.of(product));
    }

    public static String showInventoryDetails(IStoreModelService storeModelService, String inventoryId)
            throws StoreException {
        Inventory inventory = storeModelService.getInventoryById(inventoryId);

        return DetailsUtil.outputDetails("Inventory ", String.valueOf(inventory.getInventoryId()),
                inventory.getInventoryLocation().getAisleNumber(), List.of(inventory.getProduct()));
    }

    public static String showCustomerDetails(IStoreModelService storeModelService, String customerId)
            throws StoreException {
        Customer customer = storeModelService.getCustomerById(customerId);
        return DetailsUtil.outputDetails("Customer ", customer.getFirstName(), customer.getCustomerLocation().getAisleNumber(),
                List.of(customer.getEmailAddress()));
    }

    public static String showBasketOfACustomer(IStoreModelService storeModelService, String customerId)
            throws StoreException {
        Basket basketOfACustomer = storeModelService.getBasketOfACustomer(customerId);
        return DetailsUtil.outputDetails("Basket ", basketOfACustomer.getBasketId(), customerId,
                List.copyOf(basketOfACustomer.getProductsMap().keySet()));
    }

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

    public static String showSensor(IStoreModelService storeModelService, String storeId, String aisleNumber,
                             String sensorId) throws StoreException {
        ISensor sensor = storeModelService.getSensorByLocationAndSensorId(storeId, aisleNumber, sensorId);
        return DetailsUtil.outputDetails("Sensor ", sensor.getSensorName(), " contains items ",
                List.of(sensor));
    }

    public static String showAppliance(IStoreModelService storeModelService, String applianceId, String storeId,
                                       String aisleNumber) throws StoreException {
        IAppliance appliance = storeModelService.getApplianceByLocationAndSensorId(storeId, aisleNumber, applianceId);
        return DetailsUtil.outputDetails("Applaince ", appliance.getApplianceName(),
                " contains items ", List.of(appliance));
    }

}
