package com.cscie97.ledger.test;

import com.cscie97.ledger.*;

import java.io.*;

/**
   * @author - Tofik Mussa
   * This class accepts commands and logs to the screen
 */
public class CommandProcessor {

    /**
     * Processes each line of command
     * @param ledger
     * @param command
     * @param lineNumber
     */
    public void processCommand(Ledger ledger, String command, int lineNumber){
        System.out.println(interactWithLedger(ledger, command, lineNumber));
    }

    /**
     * Parses commands and interacts with ledger
     * @param ledger
     * @param command
     * @param lineNumber
     * @return an object or details about an object
     */
    Object interactWithLedger(Ledger ledger, String command, int lineNumber){
        String [] commandWords = command.split(" ");
        switch(commandWords[0].toLowerCase()){
            case "create-ledger":
                try {
                    return createLedger(commandWords[1], commandWords[3], commandWords[5]);
                } catch (LedgerException e) {
                    /*
                    When it fails a warning message has already been printed from the classes
                     */
                    return "Command line number " + lineNumber + " has failed for reasons above";
                }
            case "create-account":
                try {
                    return createAccount(ledger, commandWords[1]);
                } catch (LedgerException e) {
                    return "Command line number " + lineNumber + " has failed for reasons above";
                }
            case "get-account-balance":
                    return getAccountBalance(ledger, commandWords[1]);
            case "process-transaction":
                try {
                    return processTransaction(ledger, commandWords[1], Integer.parseInt(commandWords[3]), Integer.parseInt(commandWords[5]),
                            commandWords[7], commandWords[10], commandWords[12]);
                } catch(LedgerException e){
                    return "Command line number " + lineNumber + " has failed for reasons above";
                }

            case "get-account-balances":
                return getAccountBalanceForAll(ledger);
            case "get-block":
                return getBlockInformation(ledger, commandWords[1]);
            case "get-transaction":
                return getTransactionInformation(ledger, commandWords[1]);
            case "validate":
                try {
                    ledger.validate();
                    return validateChain();
                } catch (LedgerException e) {
                    return "Command line number " + lineNumber + " has failed for reasons above";
                }
        }
        return null;
    }
    /**
     * Prints results of validation
     * @return results of validation
     */
    private String validateChain() {

        StringBuffer validateBuffer = new StringBuffer();
        validateBuffer.append("````````````````````````````````````````````````````````````````````");
        validateBuffer.append(System.getProperty("line.separator"));
        validateBuffer.append("Congratulations!!! The state of the blockchain has been verified");
        validateBuffer.append(System.getProperty("line.separator"));
        validateBuffer.append("````````````````````````````````````````````````````````````````````");

        return validateBuffer.toString();
    }

    /**
     * Prints transaction information
     * @param ledger
     * @param transactionOrderIdentifier
     * @return transaction information
     */
    private String getTransactionInformation(Ledger ledger, String transactionOrderIdentifier) {
        StringBuffer transactionBuffer = new StringBuffer();
        transactionBuffer.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        transactionBuffer.append(transactionBuffer.append(System.getProperty("line.separator")));
        transactionBuffer.append("Transaction number " + transactionOrderIdentifier+ " has been processed by UUID " +
                ledger.getTransaction(transactionOrderIdentifier).get().getTransactionId());
        transactionBuffer.append(transactionBuffer.append(System.getProperty("line.separator")));
        transactionBuffer.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        return transactionBuffer.toString();
    }

    /**
     * Prints block information
     * @param ledger
     * @param blockNumber
     * @return block information
     */
    private String getBlockInformation(Ledger ledger, String blockNumber) {
        Block block = ledger.getBlock(Integer.parseInt(blockNumber));
        StringBuffer blockBuffer = new StringBuffer();
        blockBuffer.append("=========================================================================================");
        blockBuffer.append(System.getProperty("line.separator"));
        blockBuffer.append("This is block " + block.getBlockNumber());
        blockBuffer.append(System.getProperty("line.separator"));
        blockBuffer.append("And it has the transactions with ids below ");
        blockBuffer.append(System.getProperty("line.separator"));
        for(Transaction currentTransaction : block.getTransactions()){
            blockBuffer.append(System.getProperty("line.separator"));
            blockBuffer.append(currentTransaction.getTransactionId());
        }
        blockBuffer.append(System.getProperty("line.separator"));
        blockBuffer.append("It also contains accounts for the following ");
        blockBuffer.append(System.getProperty("line.separator"));

        for(String address : block.getAccountBalanceMap().keySet()){
            blockBuffer.append(address + " has  balance of " + block.getAccountBalanceMap().get(address).getBalance() );
            blockBuffer.append(System.getProperty("line.separator"));
        }
        blockBuffer.append(System.getProperty("line.separator"));
        blockBuffer.append("=========================================================================================");
        return blockBuffer.toString();
    }

    /**
     * Prints balances for all accounts
     * @param ledger
     * @return print out of balances
     */
    private String getAccountBalanceForAll(Ledger ledger) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("Account balances so far are : ");
        stringBuffer.append(System.getProperty("line.separator"));
        for(String address : ledger.getAccountBalances().keySet()){
            stringBuffer.append(ledger.getAccountBalance(address) + " for " + address);
            stringBuffer.append(System.getProperty("line.separator"));
        }
        stringBuffer.append("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        return stringBuffer.toString();
    }

    /**
     * Processes transactions and prints results
     * @param ledger
     * @param receiver
     * @param payer
     * @param fee
     * @param amount
     * @param payload
     * @param transactionId
     * @return a print out of transaction details
     * @throws LedgerException
     */
    private String processTransaction(Ledger ledger, String transactionId, int amount, int fee,
                                      String payload, String payer, String receiver) throws LedgerException {
        Block currentBlock = ledger.getCurrentBlock();
        Account payerAcct = currentBlock.getAccountBasedOnaddress(payer);
        Account receiverAcct = currentBlock.getAccountBasedOnaddress(receiver);

        Transaction transaction = new Transaction(transactionId, amount,
                fee, payload, payerAcct, receiverAcct);

        ledger.processTransaction(transaction);
        StringBuffer transactionBuffer = new StringBuffer();
        transactionBuffer.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        transactionBuffer.append(transactionBuffer.append(System.getProperty("line.separator")));
        transactionBuffer.append(payerAcct.getAddress() + " sent " + receiverAcct.getAddress() + "  " +
                        transaction.getAmount() + " dollars");
        transactionBuffer.append(transactionBuffer.append(System.getProperty("line.separator")));
        transactionBuffer.append("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        return transactionBuffer.toString();
    }

    /**
     * Prints account balance for a single account holder
     * @param ledger
     * @param address
     * @return a print out of account details
     */
    private String getAccountBalance(Ledger ledger, String address) {
        StringBuffer accountBuffer = new StringBuffer();
        accountBuffer.append("*********************************************************");
        accountBuffer.append(accountBuffer.append(System.getProperty("line.separator")));
        accountBuffer.append(address + "'s balance is " + ledger.getAccountBalance(address));
        accountBuffer.append(accountBuffer.append(System.getProperty("line.separator")));
        accountBuffer.append("*********************************************************");
        return accountBuffer.toString();
    }

    /**
     * Initializes ledger
     * @param name
     * @param description
     * @param seed
     * @return a ledger
     * @throws LedgerException
     */
    private Ledger createLedger(String name, String description, String seed) throws LedgerException {
        return new Ledger(name, description, seed);
    }

    /**
     * Creates new account and outputs results
     * @param address
     * @param ledger
     * @return a print out of account creation confirmation
     * @throws LedgerException
     */
    private String createAccount(Ledger ledger, String address) throws LedgerException {
        Account account = ledger.createAccount(address);
        ledger.getCurrentBlock().addsNewAccountToAccountBalanceMap(account);

        StringBuffer accountBuffer = new StringBuffer();
        accountBuffer.append("*********************************************************");
        accountBuffer.append(accountBuffer.append(System.getProperty("line.separator")));
        accountBuffer.append(address + "'s account has been created");
        accountBuffer.append(accountBuffer.append(System.getProperty("line.separator")));
        accountBuffer.append("*********************************************************");

        return accountBuffer.toString();
    }

    /**
     * Reads from file and performs action for each line. Line number will be one if it fails to read.This is an add on
     * for the bootstrap mechanism of initializing a ledger and creating a ledger object. It will be part of the results
     * document
     * @param file
     * @throws LedgerException
     */
    public void processCommandFile(String file) throws CommandProcessorException {
        try{
            File ledgerFile = new File(file);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(ledgerFile));
            int lineNumber = 1;
            Ledger initializeLedger = (Ledger) interactWithLedger(null, bufferedReader.readLine(), lineNumber);
            String command;
            while((command = bufferedReader.readLine()) != null ){
                processCommand(initializeLedger, command, lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            throw new CommandProcessorException("Error reading", "Command can not be processed ", 1);
        }
    }

}
