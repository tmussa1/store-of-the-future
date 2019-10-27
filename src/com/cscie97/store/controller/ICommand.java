package com.cscie97.store.controller;

import com.cscie97.store.model.Event;
import com.cscie97.store.model.StoreException;

public interface ICommand {
    Event execute() throws StoreControllerServiceException;
}
