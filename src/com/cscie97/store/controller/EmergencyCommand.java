package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class EmergencyCommand extends AbstractCommand{

    private String emergencyType;
    private String storeId;
    private String aisleNumber;

    Logger logger = Logger.getLogger(EmergencyCommand.class.getName());

    public EmergencyCommand(String emergencyType, String storeId, String aisleNumber) {
        this.emergencyType = emergencyType;
        this.storeId = storeId;
        this.aisleNumber = aisleNumber;
    }

    @Override
    public Event execute() {
        List<Turnstile> turnstiles;
        List<Speaker> speakers;
        List<Robot> robots;

        try {
            turnstiles = this.storeModelService.getAllTurnstilesWithinAnAisle(storeId, aisleNumber);
            this.storeModelService.openTurnstiles(turnstiles);
            speakers = this.storeModelService.getAllSpeakersWithinAnAisle(storeId, aisleNumber);
            speakers.stream()
                    .forEach(speaker -> logger.info(speaker.echoAnnouncement("There is emergency " +
                            emergencyType + " in " +
                            aisleNumber + " please leave store " + storeId + " immediately")));
            robots = this.storeModelService.getAllRobotsWithinAnAisle(storeId, aisleNumber);
            Command commandForOneRobot = new Command("Address " +
                    emergencyType + " emergency in " + aisleNumber);
            Command commandForRemainingRobots = new Command("Assist customers leaving store " + storeId);
            int rand = new Random().nextInt(robots.size());
            logger.info(robots.get(rand).listenToCommand(commandForOneRobot));
            robots.stream().filter(robot -> robot != robots.get(rand))
                    .forEach(robot -> robot.listenToCommand(commandForRemainingRobots));
        } catch (StoreException e) {
            logger.warning("Error getting appliances");
        }
        return new Event(emergencyType);
    }
}
