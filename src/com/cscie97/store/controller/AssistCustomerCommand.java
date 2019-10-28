package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * When a customer carries more than 10lbs, he may call for assistance. However, this command takes individuals with
 * disabilities into consideration and anyone can ask for assistance
 * @author Tofik Mussa
 */
public class AssistCustomerCommand extends AbstractCommand {

    private String customerId;

    Logger logger = Logger.getLogger(AssistCustomerCommand.class.getName());

    /**
     *
     * @param customerId
     */
    public AssistCustomerCommand(String customerId) {
        this.customerId = customerId;
    }

    /**
     * The first robot nearby is told the weight of the basket and is sent to assist customer.
     * An extension is the turnstile will open for both the customer and the robot so they can leave store
     * @return - an assist customer type event
     */
    @Override
    public Event execute() {
        Customer customer = null;
        try {
            customer = this.storeModelService.getCustomerById(customerId);
            Basket basket = this.storeModelService.getBasketOfACustomer(customer.getCustomerId());
            double weight = calculateBasketWeight(basket);
            logger.info("Customer " + customer.getFirstName() + " with basket " + basket.getBasketId() +
                    " is requesting assistance because the basket currently weighs " + weight);
            InventoryLocation customerLocation = customer.getCustomerLocation();
            List<Robot> robots = this.storeModelService.getAllRobotsWithinAnAisle(customerLocation.getStoreId(),
                    customerLocation.getAisleNumber());
            logger.info("Robot "+ robots.get(0) + " assigned to help customer " + customer.getFirstName() + " to car");
            Command command = new Command("Help customer " + customer.getFirstName() + " in aisle " +
                    customerLocation.getAisleNumber() + " to get to his/her car");
            logger.info(robots.get(0).listenToCommand(command));
            List<Turnstile> turnstiles = this.storeModelService
                    .getAllTurnstilesWithinAnAisle(customer.getCustomerLocation().getStoreId(),
                            customer.getCustomerLocation().getAisleNumber());
            this.storeModelService.openTurnstiles(turnstiles);
            logger.info("The customer has already checked out at this point and turnstile" +turnstiles.get(0)+
                    "is opening for customer to exit");
            logger.info("Turnstile " + turnstiles.get(1) + " opening for assisting robot");
            this.storeModelService.closeTurnstiles(turnstiles);
            logger.info("Turnstiles closed");
        } catch (StoreException e) {
            logger.warning("Robot unable to assist customer ");
        }
        return new Event(AssistCustomerCommand.class.getName());
    }

    /**
     * Calculates the total weight of a basket
     * @param basket
     * @return weight of a basket
     */
    private double calculateBasketWeight(Basket basket) {
        return basket.getProductsMap().keySet()
                .stream().map(product -> product.getVolume())
                .reduce(0.0, Double::sum);
    }
}
