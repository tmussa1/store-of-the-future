package com.cscie97.store.controller;

import com.cscie97.store.model.Command;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.Robot;
import com.cscie97.store.model.StoreException;

import java.util.logging.Logger;

public class CleanStoreCommand extends AbstractCommand {

    private String mess;
    private String storeId;
    private String aisleNumber;

    Logger logger = Logger.getLogger(CleanStoreCommand.class.getName());

    public CleanStoreCommand(String mess, String storeId, String aisleNumber) {
        this.mess = mess;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
    }

    @Override
    public Event execute() {
        try {
            Robot robot = this.storeModelService.getAllRobotsWithinAnAisle(storeId, aisleNumber).get(0);
            logger.info("Robot " + robot.getApplianceId() + " is assigned to clean the mess");
            Command robotCommand = new Command("Robot cleaning up " + mess + " in " + aisleNumber);
            logger.info(robot.listenToCommand(robotCommand));
        } catch (StoreException e) {
            logger.warning("Robot unable to clean the store ");
        }
        return null;
    }
}
