package com.cscie97.store.controller;

import com.cscie97.ledger.LedgerException;
import com.cscie97.store.model.Event;

import java.util.logging.Logger;

/**
 * Initializes ledger
 * @author Tofik Mussa
 */
public class CreateLedgerCommand extends AbstractCommand{

    private String ledgerName;
    private String ledgerDescription;
    private String ledgerSeed;

    Logger logger = Logger.getLogger(CreateLedgerCommand.class.getName());
    /**
     *
     * @param ledgerName
     * @param ledgerDescription
     * @param ledgerSeed
     * @throws LedgerException
     */
    public CreateLedgerCommand(String ledgerName, String ledgerDescription, String ledgerSeed) throws LedgerException {
        super(ledgerName, ledgerDescription, ledgerSeed);
        this.ledgerName = ledgerName;
        this.ledgerDescription = ledgerDescription;
        this.ledgerSeed = ledgerSeed;
    }

    /**
     * Initializes ledger
     * @return a create ledger type event
     */
    @Override
    public Event execute() {
        logger.info("Initialized " + ledger.toString());
        return new Event(CreateLedgerCommand.class.getName());
    }

}
