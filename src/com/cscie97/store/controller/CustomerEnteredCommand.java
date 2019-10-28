package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * This happens when a customer tries to enter store
 * @author Tofik Mussa
 */
public class CustomerEnteredCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;
    private String turnstileId;

    Logger logger = Logger.getLogger(CustomerEnteredCommand.class.getName());

    /**
     *
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @param turnstileId
     */
    public CustomerEnteredCommand(String customerId, String storeId, String aisleNumber, String turnstileId) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.turnstileId = turnstileId;
    }

    /**
     * Guests may come with a registered customer so all turnstiles near the aisle are opened.
     * The first turnstile will be delegated to the primary customer.
     * The sequence of actions is as follows
     * 1 - Basket is assigned to a customer if there is not one associated with him already
     * 2 - Since the customer is entering a store, his location is updated to the current store he is entering
     * 3 - The customer's balance is looked up from his block chain account
     * 4 - A turnstile is opened for the customer and additional turnstiles may be open for guests coming with the customer
     * 5 - A welcome message is echoed by the speaker
     * @return - a customer entered type event
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
            Command speakerCommand = new Command("Welcome customer " + customer.getFirstName() +
                    " to " + location.getStoreId());
            List<Speaker> speakers = this.storeModelService.getAllSpeakersWithinAnAisle(location.getStoreId(),
                    location.getAisleNumber());
            logger.info(speakers.get(0).echoAnnouncement(speakerCommand));
            this.storeModelService.closeTurnstiles(turnstiles);
            logger.info("Turnstiles closed after customers entered");
        } catch (StoreException e) {
            logger.warning("Customer unable to enter the store");
        }

        return new Event(CustomerEnteredCommand.class.getName());
    }
}
