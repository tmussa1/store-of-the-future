package com.cscie97.store.controller;

import com.cscie97.ledger.Ledger;
import com.cscie97.ledger.LedgerException;
import com.cscie97.store.model.Event;
import com.cscie97.store.model.IStoreModelService;
import com.cscie97.store.model.StoreModelService;

import java.util.concurrent.Callable;

public abstract class AbstractCommand implements Callable<Event>, ICommand{

    IStoreModelService storeModelService;
    Ledger ledger;
    private String authKey;

    public AbstractCommand(String ledgerName, String ledgerDescription, String ledgerSeed)
            throws LedgerException {
        this.ledger = new Ledger(ledgerName, ledgerDescription, ledgerSeed);
        this.storeModelService = StoreModelService.getInstance();
        setAuthKey("opaque-string");
        this.storeModelService.setAuthKey(getAuthKey());
    }

    public AbstractCommand() {
        this.storeModelService = StoreModelService.getInstance();
    }

    @Override
    public Event call() throws Exception {
        return this.execute();
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
