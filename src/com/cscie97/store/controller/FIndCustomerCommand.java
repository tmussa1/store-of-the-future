package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.logging.Logger;

public class FIndCustomerCommand extends AbstractCommand {

    private String firstName;

    Logger logger = Logger.getLogger(FIndCustomerCommand.class.getName());

    public FIndCustomerCommand(String customerName) {
        this.firstName = customerName;
    }

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
