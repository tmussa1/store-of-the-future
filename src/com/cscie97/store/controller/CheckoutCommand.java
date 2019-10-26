package com.cscie97.store.controller;

import com.cscie97.ledger.Account;
import com.cscie97.ledger.LedgerException;
import com.cscie97.ledger.Transaction;
import com.cscie97.store.model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class CheckoutCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;
    private String turnstileId;

    Logger logger = Logger.getLogger(CheckoutCommand.class.getName());

    public CheckoutCommand(String customerId, String storeId, String aisleNumber, String turnstileId) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.turnstileId = turnstileId;
    }

    @Override
    public Event execute() {
        Customer customer;
        Store store;
        try {
            customer = this.storeModelService.getCustomerById(customerId);
            store = this.storeModelService.getStoreById(storeId);
            logger.info("Customer " + customer.getFirstName() + " wants to leave store " + store.getStoreName());
            Basket basket = this.storeModelService.getBasketOfACustomer(customer.getCustomerId());
            logger.info("Customer is associated with basket " + basket.getBasketId());
            Map<Product, Integer> basketItems = this.storeModelService.getBasketItems(basket.getBasketId());
            basketItems.keySet().stream()
                    .forEach(product -> logger.info("Basket contains item " + product.getProductId()));
            int amountDue = calculateTotal(basketItems);
            logger.info("The amount due  for customer at checkout is " + amountDue);
            Account storeAcct = this.ledger.getAccountByAddress(store.getStoreId());
            Account customerAcct = this.ledger.getAccountByAddress(customer.getAccountAddress());
            logger.info("Store account " + storeAcct.getAddress() + " found with balance " +
                    storeAcct.getBalance());
            logger.info("Customer account " + customerAcct.getAddress() + " found with balance " +
                    customerAcct.getBalance());
            Transaction transaction = new Transaction("tran"+ UUID.randomUUID(), amountDue,
                    10, "payload"+ UUID.randomUUID(), customerAcct, storeAcct);
            String confirmation = this.ledger.processTransaction(transaction);
            logger.info("Transaction processed with confirmation " + confirmation);
            List<Turnstile> turnstiles = this.storeModelService
                    .getAllTurnstilesWithinAnAisle(storeId, aisleNumber);
            this.storeModelService.openTurnstiles(turnstiles);
            logger.info("Turnstile " + turnstiles.get(0) + " opened for customer ");
            logger.info("Goodbye " + customer.getFirstName() + " for shopping at " + store.getStoreName());
        } catch (StoreException | LedgerException e) {
            logger.warning("Customer unable to checkout");
        }
        return null;
    }

    private int calculateTotal(Map<Product, Integer> basketItems) {
        return basketItems.keySet()
                .stream()
                .map(product -> product.getPrice() * basketItems.get(product))
                .reduce(0, Integer::sum);
    }
}
