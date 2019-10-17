package com.cscie97.store.model.test;

import com.cscie97.store.model.*;

public class UpdateUtil {

    public static String updateInventoryCount(IStoreModelService storeModelService, String inventoryId,
                                              String count) throws StoreException {
        int countInt = parseNumber(count);
        storeModelService.updateInventoryCount(inventoryId, countInt);
        return DetailsUtil.outputUpdateConfirmation(inventoryId, " inventory count updated to " + countInt );
    }

    public static String updateCustomerLocation(IStoreModelService storeModelService, String customerId, String storeId,
                                                String aisleNumber) throws StoreException {
        InventoryLocation inventoryLocation = storeModelService.updateCustomerLocation(customerId, storeId, aisleNumber);
        return DetailsUtil.outputUpdateConfirmation(customerId,
                " location of customer updated to " + inventoryLocation.getAisleNumber());
    }

    public static String addBasketItem(IStoreModelService storeModelService, String basketId, String productId,
                                          String count) throws StoreException {
        int countInt = parseNumber(count);
        Basket basket = storeModelService.addItemToBasket(basketId, productId, countInt);
        return DetailsUtil.outputUpdateConfirmation(basket.getBasketId(),
                " product count in basket updated to " + countInt);
    }

    public static String removeItemFromBasket(IStoreModelService storeModelService, String basketId, String productId,
                                              String countReturned) throws StoreException {
        int countReturnedInt = parseNumber(countReturned);
        Basket basket = storeModelService.removeItemFromBasket(basketId, productId, countReturnedInt);
        return DetailsUtil.outputUpdateConfirmation(basket.getBasketId(), " item with " + productId +
                " removed from basket");
    }

    public static String clearBasketAndRemoveCustomerAssociation(IStoreModelService storeModelService, String basketId)
            throws StoreException {
        Customer customer = storeModelService.clearBasketAndRemoveAssociationWithACustomer(basketId);
        return DetailsUtil.outputUpdateConfirmation(customer.getFirstName(), " the customer's association " +
                "to the basket has been cleared");
    }

    private static int parseNumber(String count) {
        if(count.charAt(0) == '-'){
            count = count.substring(1);
            return -Integer.parseInt(count);
        }
        return Integer.parseInt(count);
    }
}
