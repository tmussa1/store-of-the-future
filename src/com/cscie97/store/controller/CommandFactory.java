package com.cscie97.store.controller;

import com.cscie97.store.model.Event;
import com.cscie97.store.model.IAppliance;
import com.cscie97.store.model.ISensor;

public class CommandFactory {

    public static AbstractCommand createCommand(Event event, ISensor sensor){
        return null;
    }

    public static AbstractCommand createCommand(Event event, IAppliance appliance){
        return null;
    }

}
