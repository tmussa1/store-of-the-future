package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
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
            List<Speaker> speakers = this.storeModelService.getAllSpeakersWithinAnAisle(
                    customer.getCustomerLocation().getStoreId(), customer.getCustomerLocation().getAisleNumber());
            Command speakerCommand = new Command("Customer " + customer.getFirstName() + "'s balance is " + accountBalance);
            logger.info(speakers.get(0).echoAnnouncement(speakerCommand));
        } catch (StoreException e) {
            logger.info("Error checking account balance");
        }
        return null;
    }
}
