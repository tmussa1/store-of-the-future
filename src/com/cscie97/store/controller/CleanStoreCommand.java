package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.logging.Logger;

public class CleanStoreCommand extends AbstractCommand {

    private String mess;
    private String storeId;
    private String aisleNumber;
    private String shelfId;

    Logger logger = Logger.getLogger(CleanStoreCommand.class.getName());

    public CleanStoreCommand(String mess, String storeId, String aisleNumber, String shelfId) {
        this.mess = mess;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;

    }

    /**
     * An extension from the requirements is when a product is dropped, inventory count gets updated since
     * that particular item will no longer be for sale
     * @return - a clean store commmand
     */
    @Override
    public Event execute() {
        try {
            Robot robot = this.storeModelService.getAllRobotsWithinAnAisle(storeId, aisleNumber).get(0);
            logger.info("Robot " + robot.getApplianceId() + " is assigned to clean the mess");
            Command robotCommand = new Command("Robot cleaning up " + mess + " in " + aisleNumber);
            logger.info(robot.listenToCommand(robotCommand));

            if(mess.contains("dropped")){
                String [] splitMess = mess.split("_");
                Inventory inventory = this.storeModelService
                        .getInventoryByProductId(splitMess[1]);
                int initialCount = inventory.getCount();
                int updatedCount = this.storeModelService.updateInventoryCount(inventory.getInventoryId(),
                        -1);
                logger.info("Inventory count for " + inventory.getInventoryId() +
                        " updated from " + initialCount + " to " + updatedCount + " because the dropped item "+
                        " will no longer be for sale ");
            }
        } catch (StoreException e) {
            logger.warning("Robot unable to clean the store ");
        }
        return new Event(CleanStoreCommand.class.getName());
    }
}
