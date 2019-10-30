package com.cscie97.store.model;

import com.cscie97.store.controller.StoreControllerServiceException;

/**
 * A subject interface, an observable
 * @author Tofik Mussa
 */
public interface ISubject {
    void register(IObserver observer);
    void deregister(IObserver observer);
    void notify(Event event) throws StoreControllerServiceException;
}
