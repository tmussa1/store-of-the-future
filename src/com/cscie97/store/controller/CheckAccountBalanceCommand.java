package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CheckAccountBalanceCommand extends AbstractCommand {

    private String customerId;

    Logger logger = Logger.getLogger(CheckAccountBalanceCommand.class.getName());

    public CheckAccountBalanceCommand(String customerId) {
        this.customerId = customerId;
    }
    @Override
    public Event execute() {
        try {
            Customer customer = this.storeModelService.getCustomerById(customerId);
            logger.info("Customer " + customer.getFirstName() + "found" );
            int accountBalance = this.ledger.getAccountBalance(customer.getAccountAddress());
            Basket basket = this.storeModelService.getBasketOfACustomer(customer.getCustomerId());
            int amountDue = calculateTotal(this.storeModelService.getBasketItems(basket.getBasketId()));
            logger.info("Customer is associated with basket "+ basket.getBasketId() + " and the amount due is "
            + amountDue);
            List<Speaker> speakers = this.storeModelService.getAllSpeakersWithinAnAisle(
                    customer.getCustomerLocation().getStoreId(), customer.getCustomerLocation().getAisleNumber());
            String balanceSufficient = amountDue < accountBalance ? " sufficient balance "  : " no sufficient balance ";
            Command speakerCommand = new Command("Customer " + customer.getFirstName() + " amount due is  " +
                    amountDue + " so you have " + balanceSufficient + " and your account balance is " + accountBalance);
            logger.info(speakers.get(0).echoAnnouncement(speakerCommand));
        } catch (StoreException e) {
            logger.info("Error checking account balance");
        }
        return new Event(CheckAccountBalanceCommand.class.getName());
    }

    private int calculateTotal(Map<Product, Integer> basketItems) {
        return basketItems.keySet()
                .stream()
                .map(product -> product.getPrice() * basketItems.get(product))
                .reduce(0, Integer::sum);
    }
}
