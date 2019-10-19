package com.cscie97.ledger.test;

import java.util.logging.Logger;
/**
    * @author - Tofik Mussa
    * The purpose of this class is to drive taking command line argument and passing to the CommandProcessor while
    * handling exceptions that may have been raised(Say a file not being found)
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
