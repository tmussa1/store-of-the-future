package com.cscie97.store.model.test;

import java.util.logging.Logger;

/**
 * This is an entry point of the application. Takes a script and delegates parsing to command processor
 * @author Tofik Mussa
 */
public class TestDriver {

    public static void main(String [] args){
        Logger logger = Logger.getLogger(TestDriver.class.getName());
        CommandProcessor commandProcessor = new CommandProcessor();
        try {
            commandProcessor.processCommandFile(args[0]);
        }
        catch (CommandProcessorException e) {
            logger.warning(e.getCommand() + "command can not be processed for " + e.getReason());
        }
    }
}
