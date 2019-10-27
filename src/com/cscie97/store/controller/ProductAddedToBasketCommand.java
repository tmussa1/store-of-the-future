package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.logging.Logger;

/**
 * Similar to product removed from basket command, this command is called to keep inventory count in sync and to
 * track items in a basket
 * @author Tofik Mussa
 */
public class ProductAddedToBasketCommand extends AbstractCommand {

    private String productId;
    private String customerId;
    private String storeId;
    private String aisleNumber;
    private String shelfId;

    Logger logger = Logger.getLogger(ProductAddedToBasketCommand.class.getName());

    /**
     *
     * @param productId
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     */
    public ProductAddedToBasketCommand(String productId, String customerId, String storeId, String aisleNumber, String shelfId) {
        this.productId = productId;
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.shelfId = shelfId;
    }

    /**
     * It is assumed that the customer picks one item at a time. The inventory gets updated to reflect for the count that
     * got taken away and then a robot restocks the shelf to bring it back to initial count.
     * @return - customer added product to basket event
     */
    @Override
    public Event execute() {
        try {
            Customer customer= this.storeModelService.getCustomerById(customerId);
            Basket basket = this.storeModelService.getBasketOfACustomer(customer.getCustomerId());
            Product product = this.storeModelService.getProductById(productId);
            basket.addProductToBasket(product, 1);
            logger.info("Customer "+ customer.getFirstName() + " added " +
                    product.getProductName() + " to basket " + basket.getBasketId());
            Inventory inventory = this.storeModelService.getInventoryByProductId(productId);
            int updatedCount = this.storeModelService.updateInventoryCount(inventory.getInventoryId(), -1);
            logger.info("The inventory " + inventory.getInventoryId() + " has been updated to reflect what the " +
                    "customer picked up and the new inventory count for "+ product.getProductName() + " is " +
                    updatedCount);
            Robot robot = this.storeModelService.getAllRobotsWithinAnAisle(inventory.getInventoryLocation().getStoreId(),
                    inventory.getInventoryLocation().getAisleNumber()).get(0);
            Command robotCommand = new Command("Restock shelf " + inventory.getInventoryLocation().getShelfId() +
                    " because " + product.getProductName() + " has been removed ");
            logger.info(robot.listenToCommand(robotCommand));
            int updatedAgainCount = this.storeModelService.updateInventoryCount(inventory.getInventoryId(), 1);
            logger.info("Robot " + robot.getApplianceId() + " has restocked the shelf and updated count for " +
                    inventory.getInventoryId() + " is brought back to " + updatedAgainCount);
        } catch (StoreException e) {
            logger.warning("Updating customer's basket failed ");
        }
        return new Event(ProductAddedToBasketCommand.class.getName());
    }
}
