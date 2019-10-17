package com.cscie97.ledger;


import jdk.jfr.Unsigned;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

/*
    @author - Tofik Mussa
    This represents a block which contains a maximum of 10 transactions and is linked to the block created before it with
    the previousHash attribute. Transactions passing validations are added to the block object and it also contains all of the
    accounts that has been created leading up to it keyed by their unique address.
*/
public class Block implements Serializable {

    /*
    blockNumbers are supposed to stay positive
     */
    @Unsigned
    private int blockNumber;

    private String previousHash;
    private String hash;
    private List<Transaction> transactions;
    private Map<String, Account> accountBalanceMap;

    /*
    Used to gracefully output to exceptions to the console
     */
    Logger logger = Logger.getLogger(Block.class.getName());

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>();
        /*
        With the exception of the genesisBlock which gets reset to 1, all block's blockNumber is set to the previous block's
         blockNumber incremented by 1
         */
        setBlockNumber(getBlockNumber() + 1);
    }

    public String generateHash(String seed){

        String hashResult = null;

        try {

            /*
            I have implemented serializable interface
             */
            String serializedTransactions = transactions.toString();

            String serializedAccountBalanceMap = accountBalanceMap.toString();

            /*
            All of the attributes except the hash field itself
             */
            String allAttributes = getBlockNumber() + getPreviousHash() + serializedTransactions + serializedAccountBalanceMap;

            /*
            These 3 lines are burrowed from https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java
             */
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            /*
            Using the seed as a salt
             */
            messageDigest.update(seed.getBytes());
            byte[] hashVal = messageDigest.digest(allAttributes.getBytes(StandardCharsets.UTF_8));
            hashResult = Base64.getEncoder().encodeToString(hashVal);

        } catch (NoSuchAlgorithmException e) {
            logger.warning("Hashing algorithm not found");
        }

        return hashResult;
    }


    public String getHash() {
        return hash;
    }

    /*
    This is used to set the generated hash once transaction limit is reached. The hash is being computed only when the Ledger
    is ready to move forward with the next block
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    /*
    No setter for previousHash to enforce immutability once block is created
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /*
    This is used to reset the blockNumber for the genesis block
     */
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Account> getAccountBalanceMap() {
        return accountBalanceMap;
    }

    /*
    This is used to pass the current accountBalanceMap to the next block once transaction limit is reached
     */
    public void setAccountBalanceMap(Map<String, Account> accountBalanceMap) {
        this.accountBalanceMap = accountBalanceMap;
    }

    /*
    Adds account to accountBalanceMap
     */

    public void addsNewAccountToAccountBalanceMap(Account account){
        this.accountBalanceMap.put(account.getAddress(), account);
    }

    /*
    This method increases account balance by the requested amount. The account's setter method throws exception for
    invalid amounts(say if new balance exceeds Integer.MAX_VAlUE)
     */
    public void increaseAccountBalance(String address, int balanceToAdd){
        Account account = this.accountBalanceMap.get(address);

        try {
            account.setBalance(account.getBalance() + balanceToAdd);
        }
        catch (LedgerException ledgerException) {
            logger.warning("Balance can not be set " + ledgerException.getReason());
        }

        /*
        replace the account entry in the map with the one with updated balance if passing validation
         */
        accountBalanceMap.replace(address, account);
    }

    /*
   This method decreases account balance by the requested amount. The account's setter method throws exception for
   invalid amounts(say if new balance becomes below zero)
    */
    public void decreaseAccountBalance(String address, int balanceToDeduct){
        Account account = this.accountBalanceMap.get(address);

        try {
            account.setBalance(account.getBalance() - balanceToDeduct);
        }
        catch (LedgerException ledgerException) {
            logger.warning("Balance can not be set " + ledgerException.getReason());
        }

        /*
        replace the account entry in the map with the one with updated balance if passing validation
         */
        accountBalanceMap.replace(address, account);
    }

    /*
    gets Account based on address
     */
    public Account getAccountBasedOnaddress(String address){
        return this.accountBalanceMap.get(address);
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    /*
    Helper method to determine the size of transactions in a block
     */
    public boolean isTransactionSizeUnderLimit(){
        return transactions.size() <= 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return blockNumber == block.blockNumber &&
                Objects.equals(previousHash, block.previousHash) &&
                Objects.equals(transactions, block.transactions) &&
                Objects.equals(accountBalanceMap, block.accountBalanceMap);
    }

    @Override
    public int hashCode() {

        return Objects.hash(blockNumber, previousHash, hash, transactions, accountBalanceMap);
    }
}
