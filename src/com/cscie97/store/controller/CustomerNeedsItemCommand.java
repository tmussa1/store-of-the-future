package com.cscie97.store.controller;

import com.cscie97.store.model.Customer;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.Product;
import com.cscie97.store.model.StoreException;

import java.util.logging.Logger;

public class CustomerNeedsItemCommand extends AbstractCommand {

    private String customerId;
    private String productId;
    private int count;

    Logger logger = Logger.getLogger(CustomerNeedsItemCommand.class.getName());

    public CustomerNeedsItemCommand(String customerId, String productId, int count) {
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
    }

    @Override
    public Event execute()  {
        try {
            Customer customer = this.storeModelService.getCustomerById(customerId);
            Product product = this.storeModelService.getProductById(productId);
            logger.info("Customer " + customer.getFirstName() + " needs an item " + product.getProductName());


        } catch (StoreException e) {
            logger.warning("Robot unable to get item for customer");
        }

        return null;
    }
}
