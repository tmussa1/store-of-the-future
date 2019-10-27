package com.cscie97.store.controller;

import com.cscie97.store.model.Event;

/**
 * Top level interface for all of the commands
 * @author Tofik Mussa
 */
public interface ICommand {
    Event execute() throws StoreControllerServiceException;
}
