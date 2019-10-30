package com.cscie97.store.controller;

import com.cscie97.store.model.Event;
import com.cscie97.store.model.IObserver;
import com.cscie97.store.model.StoreModelService;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * This class is a concrete observer that knows about the state of the Store Model Service when it changes.
 * This class also orchestrates actions based on the changes
 * @author Tofik Mussa
 */
public class StoreControllerService implements IObserver {

    Deque<AbstractCommand> commands;
    StoreModelService storeModelService;
    private String controllerName;

    Logger logger = Logger.getLogger(StoreControllerService.class.getName());

    /**
     * This constructor registers interest to listen to Store Model Service
     * @param controllerName
     */
    public StoreControllerService(String controllerName) {
        this.controllerName = controllerName;
        this.commands = new ArrayDeque<>();
        this.storeModelService = StoreModelService.getInstance();
        interestedToListen();
    }

    /**
     * This method is used by the Store Model Service to push changes in state
     * @param event
     * @throws StoreControllerServiceException
     */
    @Override
    public void update(Event event) throws StoreControllerServiceException {
        AbstractCommand command = CommandFactory.createCommand(event);
        commands.add(command);
    }

    /**
     * Registers interest to listen to commands
     */
    public void interestedToListen(){
        this.storeModelService.register(this);
    }

    /**
     * Deregisters and SCS stops listening
     */
    public void stopListening(){
        this.storeModelService.deregister(this);
    }

    /**
     * Used for adding commands as they come in to be executed later
     * @param command
     * @return a command
     */
    public ICommand addCommands(AbstractCommand command){
        commands.add(command);
        return command;
    }

    /**
     * Forces commands stored in queue to be executed and clears the queue
     */
    public void invokeCommands(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            executorService.invokeAll(commands);
            commands.clear();
        } catch (InterruptedException e) {
            logger.warning("Error invoking commands");
        }
    }

    /**
     * A name used to differentiate if there happens to be a need for more than one observer
     * @return controller name
     */
    public String getControllerName() {
        return controllerName;
    }

    @Override
    public String toString() {
        return "StoreControllerService{" +
                "controllerName='" + controllerName + '\'' +
                '}';
    }
}
