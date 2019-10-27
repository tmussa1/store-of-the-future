package com.cscie97.store.model;

/**
 * A subject interface, an observable
 * @author Tofik Mussa
 */
public interface ISubject {
    void register(IObserver observer);
    void deregister(IObserver observer);
    void notify(Event event);
}
