package com.cscie97.store.controller;

import com.cscie97.ledger.Account;
import com.cscie97.ledger.LedgerException;
import com.cscie97.ledger.Transaction;
import com.cscie97.store.model.Event;

import java.util.logging.Logger;

/**
 * Commits transactions between accounts
 * @author Tofik Mussa
 */
public class ProcessTransactionCommand extends AbstractCommand {

    private String transactionOrderIdentifier;
    private int amount;
    private int fee;
    private String payload;
    private String payerAddress;
    private String receiverAddress;

    Logger logger = Logger.getLogger(ProcessTransactionCommand.class.getName());

    /**
     *
     * @param transactionOrderIdentifier
     * @param amount
     * @param fee
     * @param payload
     * @param payerAddress
     * @param receiverAddress
     */
    public ProcessTransactionCommand(String transactionOrderIdentifier, int amount, int fee, String payload, String payerAddress, String receiverAddress) {
        this.transactionOrderIdentifier = transactionOrderIdentifier;
        this.amount = amount;
        this.fee = fee;
        this.payload = payload;
        this.payerAddress = payerAddress;
        this.receiverAddress = receiverAddress;
    }

    /**
     * Finds payer and receiver then commits transaction
     * @return a process transaction type event
     */
    @Override
    public Event execute() {
        try {
            Account payer = this.ledger.getAccountByAddress(payerAddress);
            logger.info("Payer " + payer.getAddress() + " is found ");
            Account receiver = this.ledger.getAccountByAddress(receiverAddress);
            logger.info("Receiver " + receiver.getAddress() + " is found ");
            Transaction transaction = new Transaction(transactionOrderIdentifier, amount, fee, payload, payer, receiver);
            String confirmation = this.ledger.processTransaction(transaction);
            logger.info("Transaction has been processed with confirmation " + confirmation);
        } catch (LedgerException e) {
            logger.warning("Error processing transaction");
        }
        return new Event(ProcessTransactionCommand.class.getName());
    }
}
