package com.cscie97.ledger;

/**
    * @author - Tofik Mussa
    * Utility class for exceptions thrown during validation failures
*/
public class LedgerException extends Exception {

    private String action;
    private String reason;

    /**
     *
     * @param action
     * @param reason
     */
    public LedgerException(String action, String reason) {
        this.action = action;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getAction() {
        return action;
    }
}
