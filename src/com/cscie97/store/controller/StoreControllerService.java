package com.cscie97.store.controller;

import com.cscie97.store.model.Event;
import com.cscie97.store.model.IAppliance;
import com.cscie97.store.model.IObserver;
import com.cscie97.store.model.ISensor;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class StoreControllerService implements IObserver {

    Collection<AbstractCommand> commands;

    Logger logger = Logger.getLogger(StoreControllerService.class.getName());

    public StoreControllerService() {
        this.commands = new ArrayDeque<AbstractCommand>();
    }

    @Override
    public void update(ISensor sensor, Event event) {
        AbstractCommand command = CommandFactory.createCommand(event, sensor);
        commands.add(command);
    }

    @Override
    public void update(IAppliance appliance, Event event) {
        AbstractCommand command = CommandFactory.createCommand(event, appliance);
        commands.add(command);
    }

    public void invokeCommands(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            executorService.invokeAll(commands);
            commands.clear();
        } catch (InterruptedException e) {
            logger.warning("Error invoking commands");
        }
    }
}
