package com.cscie97.store.model;

public interface IObserver {
    void update(ISensor sensor, Event event);
    void update(IAppliance appliance, Event event);
}
