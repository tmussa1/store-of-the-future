package com.cscie97.store.controller;

import com.cscie97.ledger.Account;
import com.cscie97.ledger.LedgerException;
import com.cscie97.ledger.Transaction;
import com.cscie97.store.model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * This command gets called when a customer approaches a turnstile trying to exi
 * @author Tofik Mussa
 */
public class CheckoutCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;
    private String turnstileId;

    Logger logger = Logger.getLogger(CheckoutCommand.class.getName());

    /**
     *
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @param turnstileId
     */
    public CheckoutCommand(String customerId, String storeId, String aisleNumber, String turnstileId) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
        this.turnstileId = turnstileId;
    }

    /**
     * The following happen in sequence during check out
     * 1 - customer and his basket are identified
     * 2 - the items in a basket are identified and the amount due is computed
     * 3 - the customer's balance in his block chain account is obtained and whether he has sufficient balance is determined
     * 4 - A transaction is sent to be processed by the ledger service and if successful a confirmation is echoed
     * 5 - A turnstile is opened for the customer and a goodbye message is echoed
     * @return a check out type event
     */
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
            double weight = calculateBasketWeight(basket);
            if(weight >= 10.0){
                logger.info("Basket "+ basket.getBasketId() + " has weight bigger than 10lbs");

            }
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
            Command speakerCommand = new Command("Goodbye " + customer.getFirstName() + " for shopping at "
                    + store.getStoreName());
            List<Speaker> speakers = this.storeModelService.getAllSpeakersWithinAnAisle(store.getStoreId(),
                    aisleNumber);
            logger.info(speakers.get(0).echoAnnouncement(speakerCommand));
            this.storeModelService.closeTurnstiles(turnstiles);
            logger.info("Turnstiles closing after customer left");
        } catch (StoreException | LedgerException e) {
            logger.warning("Customer unable to checkout");
        }
        return new Event(CheckoutCommand.class.getName());
    }

    /**
     * Calculates total value of items in a basket
     * @param basketItems
     * @return total value of items in a basket
     */
    private int calculateTotal(Map<Product, Integer> basketItems) {
        return basketItems.keySet()
                .stream()
                .map(product -> product.getPrice() * basketItems.get(product))
                .reduce(0, Integer::sum);
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
