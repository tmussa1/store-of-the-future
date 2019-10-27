package com.cscie97.store.controller;

import com.cscie97.ledger.Account;
import com.cscie97.ledger.LedgerException;
import com.cscie97.store.model.Event;

import java.util.logging.Logger;

/**
 * Creates a new block chain account
 * @author Tofik Mussa
 */
public class CreateAccountCommand extends AbstractCommand{
    private String accountAddress;

    Logger logger = Logger.getLogger(CreateAccountCommand.class.getName());

    /**
     *
     * @param accountAddress
     */
    public CreateAccountCommand(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    /**
     * Create a new account with the given address
     * @return a create account type event
     */
    @Override
    public Event execute() {
        try {
            Account account = this.ledger.createAccount(accountAddress);
            logger.info("Account " + account.getAddress() + " created");
        } catch (LedgerException e) {
            logger.warning("Account creation failed");
        }
        return new Event(CreateAccountCommand.class.getName());
    }
}
