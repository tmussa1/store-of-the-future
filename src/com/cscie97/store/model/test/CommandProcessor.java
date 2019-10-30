package com.cscie97.store.model.test;

import com.cscie97.store.controller.StoreControllerService;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.IStoreModelService;
import com.cscie97.store.model.StoreException;
import com.cscie97.store.model.StoreModelService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class reads script from file and processes them
 * @author Tofik Mussa
 */
public class CommandProcessor {

    /**
     * Interprets commands from the scripts
     * @param storeModelService
     * @param command
     * @param lineNumber
     * @return
     */
    private String processCommand(IStoreModelService storeModelService, String command, int lineNumber)  {
        String [] commandWords = command.split(" ");

        switch(commandWords[0].toLowerCase()){
            case "define-store":
                   try {
                       return CreateUtil.createStore(storeModelService, commandWords[1], commandWords[3],
                               commandWords[5], commandWords[6], commandWords[7]);
                   } catch (StoreException e) {
                       return ExceptionUtil.outputException(lineNumber, "Store created failed" , e);
                   }
            case "show-store":
                try {
                    System.out.println(commandWords[1]);
                    return ShowUtil.showStoreDetails(storeModelService ,commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Store not found", e);
                }
            case "define-aisle":
                try{
                    String [] storeAisle = commandWords[1].split(":");
                    return CreateUtil.createAisle(storeModelService, storeAisle[0], storeAisle[1],
                            commandWords[3], commandWords[5]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Aisle creation failed", e);
                }
            case "show-aisle":
                try {
                    String [] storeAisle = commandWords[1].split(":");
                    return ShowUtil.showAisleDetails(storeModelService, storeAisle[0], storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Aisle not found", e);
                }
            case "define-shelf":
                try {
                    String [] storeAisleShelf = commandWords[1].split(":");
                    return CreateUtil.createShelf(storeModelService, storeAisleShelf[0], storeAisleShelf[1],
                            storeAisleShelf[2],
                            commandWords[3], commandWords[5], commandWords[7], commandWords[9]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Shelf not created", e);
                }
            case "show-shelf":
                try {
                    String [] storeAisleShelf = commandWords[1].split(":");
                    return ShowUtil.showShelfDetails(storeModelService, storeAisleShelf[0], storeAisleShelf[1],
                            storeAisleShelf[2]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Shelf not found", e);
                }
            case "define-product":
                    return CreateUtil.createProduct(storeModelService, commandWords[1], commandWords[3],
                            commandWords[5],commandWords[7],commandWords[9], commandWords[11], commandWords[13]);
            case "show-product":
                try {
                    return ShowUtil.showProductDetails(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Product not found", e);
                }
            case "define-inventory":
                try {
                    String [] storeAisleShelf = commandWords[3].split(":");
                    return CreateUtil.createInventory(storeModelService, commandWords[1], storeAisleShelf[0], storeAisleShelf[1],
                            storeAisleShelf[2],  commandWords[5], commandWords[7], commandWords[9]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Inventory creation failed", e);
                }
            case "show-inventory":
                try {
                    return ShowUtil.showInventoryDetails(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Inventory with the id not found", e);
                }
            case "update-inventory":
                try{
                    return UpdateUtil.updateInventoryCount(storeModelService, commandWords[1], commandWords[3]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Inventory count not updated", e);
                }
            case "define-customer":
                try{
                    return CreateUtil.createCustomer(storeModelService, commandWords[1], commandWords[3],
                            commandWords[3], commandWords[5], commandWords[7], commandWords[9]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Customer not created", e);
                }
            case "update-customer":
                try {
                    String [] storeAisle = commandWords[3].split(":");
                    return UpdateUtil.updateCustomerLocation(storeModelService, commandWords[1], storeAisle[0],
                            storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Customer location not updated", e);
                }
            case "show-customer":
                try {
                    return ShowUtil.showCustomerDetails(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Customer not found", e);
                }
            case "define-basket":
                try {
                    return CreateUtil.createBasketForACustomer(storeModelService, commandWords[3], commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Basket not created for a customer", e);
                }
            case "get-customer-basket":
                try {
                    return ShowUtil.showBasketOfACustomer(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Basket of a customer not found", e);
                }
            case "add-basket-item":
                try {
                    return UpdateUtil.addBasketItem(storeModelService, commandWords[1],
                            commandWords[3], commandWords[5]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Item not added to basket", e);
                }
            case "remove-basket-item":
                try {
                    return UpdateUtil.removeItemFromBasket(storeModelService, commandWords[1],
                            commandWords[3], commandWords[5]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Item not removed from basket", e);
                }
            case "clear-basket":
                try {
                    return UpdateUtil.clearBasketAndRemoveCustomerAssociation(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Customer's association to basket not removed", e);
                }
            case "show-basket-items":
                try{
                    return ShowUtil.showBasketItems(storeModelService, commandWords[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Basket not found", e);
                }
            case "define-sensor":
                try {
                    String [] storeAisle = commandWords[7].split(":");
                    return CreateUtil.createSensor(storeModelService, commandWords[1], commandWords[3],
                            commandWords[5], storeAisle[0], storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Sensor creation failed", e);
                }
            case "show-sensor":
                try{
                    String [] storeAisle = commandWords[3].split(":");
                    return ShowUtil.showSensor(storeModelService, storeAisle[0], storeAisle[1], commandWords[1]);
                }catch(StoreException e){
                    return ExceptionUtil.outputException(lineNumber, "Sensor not found", e);
                }
            case "create-sensor-event":
                try{
                    String [] storeAisle = commandWords[3].split(":");
                    return CreateUtil.createSensorEvent(storeModelService,storeAisle[0], storeAisle[1], commandWords[1],
                            command);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Sensor event not created", e);
                }
            case "define-appliance":
                try{
                    String [] storeAisle = commandWords[7].split(":");
                    return CreateUtil.createAnAppliance(storeModelService, commandWords[1],
                            commandWords[3], commandWords[5], storeAisle[0], storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Appliance not created", e);
                }
            case "show-appliance":
                try{
                    String [] storeAisle = commandWords[3].split(":");
                    return ShowUtil.showAppliance(storeModelService, commandWords[1], storeAisle[0], storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Appliance not found", e);
                }
            case "create-appliance-event":
                try{
                    String [] storeAisle = commandWords[5].split(":");
                    return CreateUtil.createApplianceEvent(storeModelService, commandWords[1],
                            commandWords[3], storeAisle[0], storeAisle[1]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Appliance event not created", e);
                }
            case "create-appliance-command":
                try{
                    String [] storeAisle = commandWords[5].split(":");
                    return CreateUtil.createApplianceCommand(storeModelService, storeAisle[0], storeAisle[1],
                            commandWords[1], commandWords[3]);
                } catch (StoreException e) {
                    return ExceptionUtil.outputException(lineNumber, "Appliance command not sent", e);
                }
            case "create-controller":
                StoreControllerService controller = new StoreControllerService(commandWords[1]);
                storeModelService.getObservers().add(controller);
                return CreateUtil.createAnObserver(storeModelService, controller);
            case "invoke-commands":
            case "entering":
            case "emergency":
            case "removes":
            case "approached_turnstile":
            case "weight_assistance":
            case "basket_value":
            case "adds":
            case "dropped":
            case "broken_glass":
            case "find_customer":
            case "wants":
            case "seen_at":
            case "create-ledger":
            case "create-account":
            case "process-transaction":
                Event event = new Event(command);
                return CreateUtil.createEventSCSListensTo(storeModelService, event);
            default:
                return DetailsUtil.beginOfScript();
        }

    }


    /**
     * Accepts a file and processes line by line
     * @param file
     * @throws CommandProcessorException
     */
    public void processCommandFile(String file) throws CommandProcessorException {
        try {
            File storeFile = new File(file);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(storeFile));
            int lineNumber = 0;
            IStoreModelService storeModelService = StoreModelService.getInstance();
            String command;
            while((command = bufferedReader.readLine()) != null){
                lineNumber++;
                System.out.println(processCommand(storeModelService, command, lineNumber));
            }
        }  catch (IOException e) {
            throw new CommandProcessorException("Error reading", "Command can not be processed ", 1);
        }
    }


}
