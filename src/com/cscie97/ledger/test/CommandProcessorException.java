package com.cscie97.ledger.test;
/**
    * @author - Tofik Mussa
    * Utility class for exceptions thrown during parsing failures
*/
public class CommandProcessorException extends Exception{
    private String command;
    private String reason;
    private int lineNumber;

    public CommandProcessorException(String command, String reason, int lineNumber) {
        this.command = command;
        this.reason = reason;
        this.lineNumber = lineNumber;
    }

    public String getCommand() {
        return command;
    }

    public String getReason() {
        return reason;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
