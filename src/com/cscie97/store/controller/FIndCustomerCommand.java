package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.logging.Logger;

/**
 * Locates missing person in the store
 * @author Tofik Mussa
 */
public class FIndCustomerCommand extends AbstractCommand {

    private String firstName;

    Logger logger = Logger.getLogger(FIndCustomerCommand.class.getName());

    /**
     *
     * @param customerName
     */
    public FIndCustomerCommand(String customerName) {
        this.firstName = customerName;
    }

    /**
     * Locates customer by name. The assumption is that it is highly unlikely to have two customers with the same given
     * name at any given time. If it turns out that there is more than one person with the given first name and the
     * customer looking for his mate is dissatisfied, another command shall be sent to find missing person until he is
     * found
     * @return a find customer type event
     */
    @Override
    public Event execute(){
        try {
            Customer customer = this.storeModelService.getCustomerByCustomerName(firstName);
            Speaker speaker = this.storeModelService.getAllSpeakersWithinAnAisle(
                    customer.getCustomerLocation().getStoreId(), customer.getCustomerLocation().getAisleNumber())
                    .get(0);
            Command speakerCommand = new Command("Customer " + customer.getFirstName() + " found in "+
                    customer.getCustomerLocation().getAisleNumber());
            logger.info(speaker.echoAnnouncement(speakerCommand));
        } catch (StoreException e) {
            logger.warning("Finding missing person not successful");
        }
        return new Event(FIndCustomerCommand.class.getName());
    }
}
