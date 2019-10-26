package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.logging.Logger;

public class CustomerEnteredCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;
    private String turnstileId;

    Logger logger = Logger.getLogger(CustomerEnteredCommand.class.getName());

    public CustomerEnteredCommand(String customerId, String storeId, String aisleNumber, String turnstileId) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.turnstileId = turnstileId;
    }

    /**
     * Guests may come with a registered customer so all turnstiles near the aisle are opened.
     * The first turnstile will be delegated to the primary customer.
     * Action's performed to successfully let a customer in are logged
     * @return - a customer entered event
     */
    @Override
    public Event execute() {
        try {
            Customer customer= this.storeModelService.getCustomerById(customerId);
            Basket basket = this.storeModelService.getBasketOfACustomer(customerId);
            logger.info("Basket " + basket.getBasketId() + " assigned to customer ");
            InventoryLocation location = this.storeModelService.
                    updateCustomerLocation(customerId, storeId, aisleNumber);
            logger.info("Customer's location updated to " + location.getStoreId() + " and " +
            location.getAisleNumber());
            int accountBalance = this.ledger.getAccountBalance(customer.getAccountAddress());
            logger.info("Customer's account balance is " + accountBalance);
            List<Turnstile> turnstiles = this.storeModelService
                    .getAllTurnstilesWithinAnAisle(storeId, aisleNumber);
            this.storeModelService.openTurnstiles(turnstiles);
            logger.info("Turnstile " + turnstiles.get(0) + " opened for customer ");
            logger.info("Welcome customer " + customer.getFirstName() + " to " + location.getStoreId());
        } catch (StoreException e) {
            logger.warning("Customer unable to enter the store");
        }

        return new Event(CustomerEnteredCommand.class.getName());
    }
}
