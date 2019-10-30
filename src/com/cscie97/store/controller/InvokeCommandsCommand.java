package com.cscie97.store.controller;

import com.cscie97.store.model.Event;

/**
 * This command forces all commands in the queue to execute
 * @author Tofik Mussa
 */
public class InvokeCommandsCommand extends AbstractCommand {

    private String message;

    public InvokeCommandsCommand(String message) {
        this.message = message;
    }

    @Override
    public Event execute() throws StoreControllerServiceException {
        return new Event(InvokeCommandsCommand.class.getName());
    }
}
