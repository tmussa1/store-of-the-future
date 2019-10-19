package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

/**
 * Utility class for update functionality
 * @author Tofik Mussa
 */
public class UpdateUtil {

    /**
     * Updates inventory count
     * @param storeModelService
     * @param inventoryId
     * @param count
     * @return confirmation of inventory updated
     * @throws StoreException
     */
    public static String updateInventoryCount(IStoreModelService storeModelService, String inventoryId,
                                              String count) throws StoreException {
        int countInt = parseNumber(count);
        storeModelService.updateInventoryCount(inventoryId, countInt);
        return DetailsUtil.outputUpdateConfirmation(inventoryId, " inventory count updated to " + countInt );
    }

    /**
     * Updates customer location
     * @param storeModelService
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @return confirmation of customer's location updated
     * @throws StoreException
     */
    public static String updateCustomerLocation(IStoreModelService storeModelService, String customerId, String storeId,
                                                String aisleNumber) throws StoreException {
        InventoryLocation inventoryLocation = storeModelService.updateCustomerLocation(customerId, storeId, aisleNumber);
        return DetailsUtil.outputUpdateConfirmation(customerId,
                " location of customer updated to " + inventoryLocation.getAisleNumber());
    }

    /**
     * Updates item's in a customer's basket
     * @param storeModelService
     * @param basketId
     * @param productId
     * @param count
     * @return confirmation of customer's basket updated
     * @throws StoreException
     */
    public static String addBasketItem(IStoreModelService storeModelService, String basketId, String productId,
                                          String count) throws StoreException {
        int countInt = parseNumber(count);
        Basket basket = storeModelService.addItemToBasket(basketId, productId, countInt);
        return DetailsUtil.outputUpdateConfirmation(basket.getBasketId(),
                " product count in basket updated to " + countInt);
    }

    /**
     * Removes items from a customer's basket
     * @param storeModelService
     * @param basketId
     * @param productId
     * @param countReturned
     * @return confirmation of items removed from basket
     * @throws StoreException
     */
    public static String removeItemFromBasket(IStoreModelService storeModelService, String basketId, String productId,
                                              String countReturned) throws StoreException {
        int countReturnedInt = parseNumber(countReturned);
        Basket basket = storeModelService.removeItemFromBasket(basketId, productId, countReturnedInt);
        return DetailsUtil.outputUpdateConfirmation(basket.getBasketId(), " item with " + productId +
                " removed from basket");
    }

    /**
     * Clears basket and removes association with customer
     * @param storeModelService
     * @param basketId
     * @return confirmation of customer/basket association removed
     * @throws StoreException
     */
    public static String clearBasketAndRemoveCustomerAssociation(IStoreModelService storeModelService, String basketId)
            throws StoreException {
        Customer customer = storeModelService.clearBasketAndRemoveAssociationWithACustomer(basketId);
        return DetailsUtil.outputUpdateConfirmation(customer.getFirstName(), " the customer's association " +
                "to the basket has been cleared");
    }

    /**
     * Parses negative and positive numbers
     * @param count
     * @return a parsed integer
     */
    private static int parseNumber(String count) {
        if(count.charAt(0) == '-'){
            count = count.substring(1);
            return -Integer.parseInt(count);
        }
        return Integer.parseInt(count);
    }
}
