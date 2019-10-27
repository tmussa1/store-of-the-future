package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.logging.Logger;

/**
 * This command gets called when a customer has trouble finding a product
 * @author Tofik Mussa
 */
public class CustomerNeedsItemCommand extends AbstractCommand {

    private String customerId;
    private String productId;
    private int count;

    Logger logger = Logger.getLogger(CustomerNeedsItemCommand.class.getName());

    /**
     *
     * @param customerId
     * @param productId
     * @param count
     */
    public CustomerNeedsItemCommand(String customerId, String productId, int count) {
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
    }

    /**
     * This extends the requirement to make it more realistic. The steps are
     * 1 - customer requests an item
     * 2 - The nearest robot gets assigned to find item for customer
     * 3 - Robot moves to the aisle where the product is located
     * 4 - Robot moves back to the customer and hands in the product of however many was requested
     * 5 - Customer's basket gets updated with the product and the corresponding count
     * 6 - Inventory count gets updated to reflect the item the customer picked up
     * @return - customer needs item type event
     */
    @Override
    public Event execute() {
        try {
            Customer customer = this.storeModelService.getCustomerById(customerId);
            Product product = this.storeModelService.getProductById(productId);
            Inventory inventory = this.storeModelService.getInventoryByProductId(productId);
            logger.info("Customer " + customer.getFirstName() + " needs an item " + product.getProductName());
            Robot robot = this.storeModelService
                    .getAllRobotsWithinAnAisle(customer.getCustomerLocation().getStoreId(),
                            customer.getCustomerLocation().getAisleNumber()).get(0);
            logger.info("Robot " + robot.getApplianceId() + " is assigned to find item to customer " +
                    customer.getFirstName());
            IAppliance robotMoved = this.storeModelService.moveRobot(robot.getApplianceLocation().getStoreId(),
                    robot.getApplianceLocation().getAisleNumber(),
                    robot.getApplianceId(), inventory.getInventoryLocation().getAisleNumber());
            Command robotMoveCommand = new Command("Nearest robot " + robot.getApplianceName() + " moved to pick up item "
                    + product.getProductName() + " from " + robot.getApplianceLocation().getAisleNumber() + " to " +
                    robotMoved.getApplianceLocation().getAisleNumber());
            logger.info(robot.listenToCommand(robotMoveCommand));
            IAppliance robotMovedAgain = this.storeModelService.moveRobot(robotMoved.getApplianceLocation().getStoreId(),
                    robotMoved.getApplianceLocation().getAisleNumber(),
                    robotMoved.getApplianceId(), customer.getCustomerLocation().getAisleNumber());
            Command robotMoveAgainCommand = new Command("Robot moved again from " + robotMoved.getApplianceLocation().getAisleNumber()
                    + " to " + robotMovedAgain.getApplianceLocation().getAisleNumber() + " to walk up to customer and hand in "+
                    product.getProductName());
            logger.info(robot.listenToCommand(robotMoveAgainCommand));
            Basket basket = this.storeModelService.getBasketOfACustomer(customerId);
            basket.addProductToBasket(product, count);
            logger.info("The customer " + customer.getFirstName() + "'s basket has been updated and now contains "
            + count + " of " + product.getProductName());
            int updateInventoryCount = this.storeModelService.updateInventoryCount(inventory.getInventoryId(), -count);
            logger.info("Invetory count has been updated to " + updateInventoryCount + " from " +
                    inventory.getCount() + " after " + count + " counts of " + product.getProductName() + " have been handed to "+
                    customer.getFirstName());

        } catch (StoreException e) {
            logger.warning("Robot unable to get item for customer");
        }

        return new Event(CustomerNeedsItemCommand.class.getName());
    }
}
