package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.logging.Logger;

public class AssistCustomerCommand extends AbstractCommand {

    private String customerId;

    Logger logger = Logger.getLogger(AssistCustomerCommand.class.getName());

    public AssistCustomerCommand(String customerId) {
        this.customerId = customerId;
    }

    /**
     * The first robot nearby is told the weight of the basket and is sent to assist customer
     * @return - an assist customer event
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
        } catch (StoreException e) {
            logger.warning("Robot unable to assist customer ");
        }
        return new Event(AssistCustomerCommand.class.getName());
    }

    private double calculateBasketWeight(Basket basket) {
        return basket.getProductsMap().keySet()
                .stream().map(product -> product.getVolume())
                .reduce(0.0, Double::sum);
    }
}
