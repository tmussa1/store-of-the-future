package com.cscie97.store.model;

import com.cscie97.store.controller.IObserver;

/**
 * A subject interface, an observable
 * @author Tofik Mussa
 */
public interface ISubject {
    public void register(IObserver observer);
    public void deregister(IObserver observer);
    public void notify(ISensor sensor, Event event);
    public void notify(IAppliance appliance, Event event);
}
