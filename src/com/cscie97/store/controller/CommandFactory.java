package com.cscie97.store.controller;

import com.cscie97.ledger.LedgerException;
import com.cscie97.store.model.Event;

import java.util.logging.Logger;

/**
 * This class implements the factory design pattern. It generates the appropriate command and adds it to the
 * queue to process later. When the invoke commands command gets called, the commands in the queue execute
 * in chunks. The client is sent a confirmation that the request is getting processed right away
 * @author Tofik Mussa
 */
public class CommandFactory {

    private static StoreControllerService storeControllerService = new StoreControllerService("Cont");

    static Logger logger = Logger.getLogger(CommandFactory.class.getName());

    /**
     * Interprets events and in the case of commands, instantiates them and puts them in a queue for later processing
     * @param event
     * @return commands
     */
    public static AbstractCommand createCommand(Event event) throws StoreControllerServiceException {
        String [] commandWords = event.getMessage().split(" ");
        switch(commandWords[0].toLowerCase()){
            case "invoke-commands":
                storeControllerService.invokeCommands();
                logger.info("The commands in the queue have been processed");
                return new InvokeCommandsCommand(commandWords[0]);
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
            case "basket_value":
                AbstractCommand checkBalanceCommand = new CheckAccountBalanceCommand(commandWords[3]);
                storeControllerService.addCommands(checkBalanceCommand);
                logger.info("Check account balance query added to queue. We will get back to you");
                return checkBalanceCommand;
            case "adds":
                String [] storeAisleShelf2 = commandWords[5].split(":");
                AbstractCommand productAddedToShelf = new ProductRemovedFromBasketCommand(commandWords[1],
                        commandWords[3], storeAisleShelf2[0], storeAisleShelf2[1], storeAisleShelf2[2]);
                storeControllerService.addCommands(productAddedToShelf);
                logger.info("Product added back to shelf command added to queue. We will get back to you");
                return productAddedToShelf;
            case "dropped":
                String [] storeAisleShelf3 = commandWords[3].split(":");
                String mess = commandWords[0] + "_" + commandWords[1];
                AbstractCommand droppedCommand = new CleanStoreCommand(mess,
                        storeAisleShelf3[0], storeAisleShelf3[1], storeAisleShelf3[2]);
                storeControllerService.addCommands(droppedCommand);
                logger.info("Product dropped to floor command added to queue. We will get back to you");
                return droppedCommand;
            case "broken_glass":
                String [] storeAisle4 = commandWords[2].split(":");
                AbstractCommand brokenGlassCommand = new CleanStoreCommand(commandWords[0],
                        storeAisle4[0], storeAisle4[1], "");
                storeControllerService.addCommands(brokenGlassCommand);
                logger.info("Broken glass command added to queue. We will get back to you");
                return brokenGlassCommand;
            case "find_customer":
                AbstractCommand missingPersonCommand = new FIndCustomerCommand(commandWords[1]);
                storeControllerService.addCommands(missingPersonCommand);
                logger.info("Missing person command added to queue. We will get back to you");
                return missingPersonCommand;
            case "wants":
                AbstractCommand customerNeedsItem = new CustomerNeedsItemCommand(commandWords[5]
                        , commandWords[3], Integer.parseInt(commandWords[1]));
                storeControllerService.addCommands(customerNeedsItem);
                logger.info("Customer needs a product command added to queue. We will get back to you");
                return customerNeedsItem;
            case "seen_at":
                String [] storeAisle5 = commandWords[1].split(":");
                AbstractCommand updateCustomerLocationCommand = new CustomerSeenCommand(commandWords[3],
                        storeAisle5[0], storeAisle5[1]);
                storeControllerService.addCommands(updateCustomerLocationCommand);
                logger.info("Customer seen command added to queue. We will get back to you");
                return updateCustomerLocationCommand;
            case "create-ledger":
                try {
                CreateLedgerCommand ledgerCommand = new CreateLedgerCommand(commandWords[1],
                        commandWords[3], commandWords[5]);
                storeControllerService.addCommands(ledgerCommand);
                logger.info("Ledger initialization request added to queue. We will get back to you");
                return ledgerCommand;
                } catch (LedgerException e) {
                    logger.warning("Ledger initialization failed");
                }
            case "create-account":
                CreateAccountCommand accountCommand = new CreateAccountCommand(commandWords[1]);
                storeControllerService.addCommands(accountCommand);
                logger.info("Create account request added to queue. We will get back to you");
                return accountCommand;
            case "process-transaction":
                ProcessTransactionCommand processTransactionCommand =
                        new ProcessTransactionCommand(commandWords[1], Integer.parseInt(commandWords[3]),
                                Integer.parseInt(commandWords[5]), commandWords[7], commandWords[9],
                                commandWords[11]);
                storeControllerService.addCommands(processTransactionCommand);
                logger.info("Process transaction added to queue. We will get back to you");
                return processTransactionCommand;
            default:
                throw new StoreControllerServiceException("Command not recognizable by the Store Controller Service");
        }
    }

    /**
     * Creates store controller service
     * This must happen before other commands since the other commands depend on this
     * @param command
     * @return an instance of store controller service
     */
    public static StoreControllerService createController(String command){
        System.out.println("Heloooooooooooooooooooooooooooooooo");
        return new StoreControllerService(command);
    }

}
