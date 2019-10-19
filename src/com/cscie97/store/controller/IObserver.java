package com.cscie97.store.controller;

import com.cscie97.store.model.Event;
import com.cscie97.store.model.IAppliance;
import com.cscie97.store.model.ISensor;

public interface IObserver {
    public void update(ISensor sensor, Event event);
    public void update(IAppliance appliance, Event event);
}
