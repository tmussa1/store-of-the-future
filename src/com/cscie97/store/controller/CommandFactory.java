package com.cscie97.store.controller;

import com.cscie97.store.model.Event;

import java.util.logging.Logger;

public class CommandFactory {

    private static StoreControllerService storeControllerService;

    static Logger logger = Logger.getLogger(CommandFactory.class.getName());

    public static AbstractCommand createCommand(Event event){
        String [] commandWords = event.getMessage().split(" ");
        switch(commandWords[0]){
            case "create-controller":
              logger.info(createController(commandWords[1]).getControllerName() + " has been created");
              break;
            case "register-controller":
                storeControllerService.interestedToListen();
                logger.info("SCS expressed interest to listen to SMS events");
                break;
            case "invoke-commands":
                storeControllerService.invokeCommands();
                logger.info("The commands in the queue have been processed");
                break;
            case "entering":
                String [] storeAisle = commandWords[3].split(":");
                AbstractCommand customerEnteredCommand =  new
                        CustomerEnteredCommand(commandWords[2], storeAisle[0], storeAisle[1], commandWords[5]);
                storeControllerService.addCommands(customerEnteredCommand);
                logger.info("Customer entered command added to queue. We will get back to you");
                return customerEnteredCommand;
            case "emergency":
                String [] storeAisle2 = commandWords[3].split(":");
                AbstractCommand emergencyCommand = new EmergencyCommand(commandWords[0],
                        storeAisle2[0], storeAisle2[1]);
                storeControllerService.addCommands(emergencyCommand);
                logger.info("Emergency command added to queue. We will get back to you");
                return emergencyCommand;
            case "removes":
                String [] storeAisleShelf = commandWords[5].split(":");
                AbstractCommand productRemovedFromShelf = new ProductAddedToBasketCommand(commandWords[1],
                        commandWords[3], storeAisleShelf[0], storeAisleShelf[1], storeAisleShelf[2]);
                storeControllerService.addCommands(productRemovedFromShelf);
                logger.info("Product added to basket command added to queue. We will get back to you");
                return productRemovedFromShelf;
            case "approached_turnstile":
                String [] storeAisle3 = commandWords[5].split(":");
                AbstractCommand checkoutCommand = new CheckoutCommand(commandWords[3],
                        storeAisle3[0], storeAisle3[1], commandWords[1]);
                storeControllerService.addCommands(checkoutCommand);
                logger.info("Check out command added to queue. We will get back to you");
                return checkoutCommand;
            case "weight_assistance":
                AbstractCommand weightAssistanceCommand = new AssistCustomerCommand(commandWords[3]);
                storeControllerService.addCommands(weightAssistanceCommand);
                logger.info("Weight assistance command added to queue. We will get back to you");
                return weightAssistanceCommand;
        }
        return null;
    }

    public static StoreControllerService createController(String command){
        if(storeControllerService == null){
            storeControllerService = new StoreControllerService(command);
        }
        return storeControllerService;
    }

}
