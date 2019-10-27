package com.cscie97.store.controller;

import com.cscie97.store.model.*;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class StoreControllerService implements IObserver {

    Collection<AbstractCommand> commands;
    StoreModelService storeModelService;
    private String controllerName;

    Logger logger = Logger.getLogger(StoreControllerService.class.getName());

    public StoreControllerService(String controllerName) {
        this.controllerName = controllerName;
        this.commands = new ArrayDeque<AbstractCommand>();
        this.storeModelService = StoreModelService.getInstance();
        interestedToListen();
    }

    @Override
    public void update(Event event) {
        AbstractCommand command = CommandFactory.createCommand(event);
        commands.add(command);
    }

    public void interestedToListen(){
        this.storeModelService.register(this);
    }

    public ICommand addCommands(AbstractCommand command){
        commands.add(command);
        return command;
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

    public String getControllerName() {
        return controllerName;
    }
}
