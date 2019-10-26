package com.cscie97.store.controller;

import com.cscie97.store.model.Customer;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.StoreException;

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
            logger.info("Customer " + customer.getFirstName() + "'s balance is " + accountBalance);
        } catch (StoreException e) {
            logger.info("Error checking account balance");
        }
        return null;
    }
}
