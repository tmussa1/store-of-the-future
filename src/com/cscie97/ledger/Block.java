package com.cscie97.ledger;


import jdk.jfr.Unsigned;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

/**
    * @author - Tofik Mussa
    * This represents a block which contains a maximum of 10 transactions and is linked to the block created before it with
    * the previousHash attribute. Transactions passing validations are added to the block object and it also contains all of the
    * accounts that has been created leading up to it keyed by their unique address.
*/
public class Block implements Serializable {

    /**
     * blockNumbers are supposed to stay positive
     */
    @Unsigned
    private int blockNumber;

    private String previousHash;
    private String hash;
    private List<Transaction> transactions;
    private Map<String, Account> accountBalanceMap;
    Logger logger = Logger.getLogger(Block.class.getName());

    /**
     *
     * @param previousHash
     */
    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>();
        setBlockNumber(getBlockNumber() + 1);
    }

    /**
     * The serializable interface is implemented. All of the attributes except the hash field itself are used to
     * generate the hash. The seed is used as salt. Lines  55 - 57 are burrowed from
     * https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java
     * @param seed
     * @return the generated hash
     */
    public String generateHash(String seed){
        String hashResult = null;
        try {
            String serializedTransactions = transactions.toString();
            String serializedAccountBalanceMap = accountBalanceMap.toString();
            String allAttributes = getBlockNumber() + getPreviousHash() + serializedTransactions + serializedAccountBalanceMap;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
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

    /**
     * @param hash
     * This is used to set the generated hash once transaction limit is reached. The hash is being computed only
     * when the Ledger is ready to move forward with the next block
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    /**
     * No setter for previousHash to enforce immutability once block is created
     * @return the previous hash
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * @param blockNumber
     * With the exception of the genesisBlock which gets reset to 1, all block's blockNumber is set to the previous block's
     * blockNumber incremented by 1
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

    /**
     * @param accountBalanceMap
     * This is used to pass the current accountBalanceMap to the next block once transaction limit is reached
     */
    public void setAccountBalanceMap(Map<String, Account> accountBalanceMap) {
        this.accountBalanceMap = accountBalanceMap;
    }

    /**
     * @param account
     * Adds account to accountBalanceMap
     */
    public void addsNewAccountToAccountBalanceMap(Account account){
        this.accountBalanceMap.put(account.getAddress(), account);
    }

    /**
     * This method increases account balance by the requested amount. The account's setter method throws exception for
     * invalid amounts(say if new balance exceeds Integer.MAX_VAlUE). It replace the account entry in the map with the
     * one with updated balance if passing validation
     * @param address
     * @param balanceToAdd
     * @throws LedgerException
     */
    public void increaseAccountBalance(String address, int balanceToAdd){
        Account account = this.accountBalanceMap.get(address);
        try {
            account.setBalance(account.getBalance() + balanceToAdd);
        } catch (LedgerException ledgerException) {
            logger.warning("Balance can not be set " + ledgerException.getReason());
        }
        accountBalanceMap.replace(address, account);
    }

    /**
     * This method decreases account balance by the requested amount. The account's setter method throws exception for
     * invalid amounts(say if new balance becomes below zero). It replace the account entry in the map with the
     * one with updated balance if passing validation
     * @param address
     * @param balanceToDeduct
     * @throws LedgerException
    */
    public void decreaseAccountBalance(String address, int balanceToDeduct){
        Account account = this.accountBalanceMap.get(address);
        try {
            account.setBalance(account.getBalance() - balanceToDeduct);
        } catch (LedgerException ledgerException) {
            logger.warning("Balance can not be set " + ledgerException.getReason());
        }
        accountBalanceMap.replace(address, account);
    }

    /**
     * gets Account based on address
     * @param address
     */
    public Account getAccountBasedOnaddress(String address){
        return this.accountBalanceMap.get(address);
    }

    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    /**
     * Helper method to determine the size of transactions in a block
     * @return whether the account is under the transaction limit
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
