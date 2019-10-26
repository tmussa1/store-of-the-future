package com.cscie97.store.controller;

import com.cscie97.store.model.Event;

public interface ICommand {
    Event execute();
}
