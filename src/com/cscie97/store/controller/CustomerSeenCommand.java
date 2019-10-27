package com.cscie97.store.controller;

import com.cscie97.store.model.Customer;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.InventoryLocation;
import com.cscie97.store.model.StoreException;

import java.util.logging.Logger;

public class CustomerSeenCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;

    Logger logger = Logger.getLogger(CustomerSeenCommand.class.getName());

    public CustomerSeenCommand(String customerId, String storeId, String aisleNumber) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
    }

    @Override
    public Event execute() {
        Customer customer = null;
        try {
            customer = this.storeModelService.getCustomerById(customerId);
            InventoryLocation customerLocation = this.storeModelService.updateCustomerLocation(customer.getCustomerId(),
                    storeId, aisleNumber);
            logger.info("Customer " + customer.getFirstName() + "'s location updated from "+ aisleNumber + " to "+
                    customer.getCustomerLocation().getAisleNumber());
        } catch (StoreException e) {
           logger.warning("Customer location not updated");
        }
        return new Event(CustomerSeenCommand.class.getName());
    }
}
