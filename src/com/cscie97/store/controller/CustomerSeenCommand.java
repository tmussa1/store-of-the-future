package com.cscie97.store.controller;

import com.cscie97.store.model.Customer;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.InventoryLocation;
import com.cscie97.store.model.StoreException;

import java.util.logging.Logger;

/**
 * This command is called when cameras sense the movement of a customer or through voice recognition
 * and a customer's location need to be updated
 * @author Tofik Mussa
 */
public class CustomerSeenCommand extends AbstractCommand {

    private String customerId;
    private String storeId;
    private String aisleNumber;

    Logger logger = Logger.getLogger(CustomerSeenCommand.class.getName());

    /**
     *
     * @param customerId
     * @param storeId
     * @param aisleNumber
     */
    public CustomerSeenCommand(String customerId, String storeId, String aisleNumber) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
    }

    /**
     * Updates the location of a customer when sensed by camera or through voice recognition
     * @return a customer seen type event
     */
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
