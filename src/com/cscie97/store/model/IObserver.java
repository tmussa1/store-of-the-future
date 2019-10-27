package com.cscie97.store.model;

import com.cscie97.store.controller.StoreControllerServiceException;

public interface IObserver {
    void update(Event event) throws StoreControllerServiceException;
}
