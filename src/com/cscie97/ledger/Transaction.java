package com.cscie97.ledger;

import jdk.jfr.Unsigned;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
    * @author - Tofik Mussa
    * This class does all of the validation work when transaction instance is created. It assigns a unique transactionId
    * if all of the values passed in to the constructor are valid.
*/
public class Transaction implements Serializable {

    private String transactionId;
    private String transactionOrderIdentifier;

    @Unsigned
    private int amount;

    private int fee;
    private String payload;
    private Account receiver;
    private Account payer;
    Logger logger = Logger.getLogger(Transaction.class.getName());

    /**
      * All of the fields pass through a validation check. If any of the setters fail, the program won't get to
      * setTransactionId(UUID.randomUUID().toString()) and a unique transactionId won't be set. All the ledger has to
      * during incoming transactions is check if the transaction has a UUID transactionId and not process it if doesn't.
      * I am disregarding the transactionId that the CommandProcessor passed during parsing. The deviation from design
      * document is justified on the results document.
      * @param transactionOrderIdentifier
      * @param amount
      * @param fee
      * @param payer
      * @param receiver
      * @param payload
      * @throws LedgerException
     */
    public Transaction(String transactionOrderIdentifier, int amount, int fee, String payload, Account payer, Account receiver) {
        try{
            setAmount(amount);
            setFee(fee);
            setPayload(payload);
            setPayer(payer);
            setReceiver(receiver);
            setTransactionOrderIdentifier(transactionOrderIdentifier);
            setTransactionId(UUID.randomUUID().toString());
        }
        catch(LedgerException ledgerException){
            logger.warning("Transaction can not be processed " + ledgerException.getReason());
            this.transactionId = null;
        }


    }

    /**
     * All of the setters are private to enforce immutability and they do validation before setting
     * @param transactionId
     */
    private void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Amount must be between 0 and Integer.MAX_VALUE to be set. Throws exception if it isn't
     * @param amount
     * @throws LedgerException
     */
    private void setAmount(int amount) throws LedgerException {
        if(isValidAmount(amount)){
            this.amount = amount;
        } else {
            throw new LedgerException("Transaction can not be processed", "Amount must be between 0 and 2147483647");
        }
    }

    /**
     * Fee must be at least $10 and throws exception if it isn't
     * @param fee
     * @throws LedgerException
     */
    private void setFee(int fee) throws LedgerException {
        if(isValidFee(fee)){
            this.fee = fee;
        } else {
            throw new LedgerException("Transaction can not be processed", "Fee can not be less than $10");
        }
    }

    /**
     * Length of payload must not be greater thatn 1024 and throws exception if it isn't
     * @param payload
     * @throws LedgerException
     */
    private void setPayload(String payload) throws LedgerException {
        if(isValidPayload(payload)){
            this.payload = payload;
        } else {
            throw new LedgerException("Transaction can not be processed", "Invalid payload");
        }
    }

    /**
     * Receiver must not be null and must have a valid unique address set by the ledger during
     * initialization. The validation to check for duplicate addresses is done prior to creation of Receiver account
     * by the ledger itself. Throws exception if receiver don't have a unique address associated with it.
     * @param receiver
     * @throws LedgerException
     */
    private void setReceiver(Account receiver) throws LedgerException {
        if(isReceiverValid(receiver)){
            this.receiver = receiver;
        } else {
            throw new LedgerException("Transaction can not be processed", "Invalid receiver");
        }
    }

    /**
     * Payer must not be null and must have a valid unique address set by the ledger during
     * initialization. The validation to check for duplicate addresses is done prior to creation of Payer account
     * by the ledger itself. The Payer must also have enough funds to cover the fees and amount of transaction. Throws
     * exception if payer doesn't have a unique address assigned to it or if it doesn't have sufficient funds
     * @param payer
     * @throws LedgerException
     */
    private void setPayer(Account payer) throws LedgerException {
        if(isPayerHavingSufficientFunds(payer) && isPayerValid(payer)){
            this.payer = payer;
        }
        else {
            throw new LedgerException("Transaction can not be processed", "Invalid payer or doesn't have sufficient funds");
        }
    }

    public Account getReceiver() {
        return receiver;
    }
    public Account getPayer() {
        return payer;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public int getAmount() {
        return amount;
    }
    public int getFee() {
        return fee;
    }

    /**
     * Helper method checking amount validity
     * @param amount
     * @return whether amount is valid
     */
    private boolean isValidAmount(int amount){
        return amount > 0 && this.amount <= Integer.MAX_VALUE;
    }

    /**
     * Helper method checking fee validity
     * @param fee
     * @return  whether fee is valid
     */
    private boolean isValidFee(int fee){
        return fee >= 10;
    }

    /**
     * Helper method checking payload validity
     * @param payload
     * @return whether payload is valid
     */
    private boolean isValidPayload(String payload){
        return payload.length() < 1024;
    }

    /**
     * Helper method checking sufficient funds of payer
     * @param payer
     * @return whether payer has sufficient funds
     */
    public boolean isPayerHavingSufficientFunds(Account payer){
        return payer.getBalance() >= (this.fee + this.amount);
    }

    /**
     * Helper method checking if the Payer's address has been set by ledger
     * @param payer
     * @return whether payer is valid
     */
    public boolean isPayerValid(Account payer){
        return payer != null && payer.getAddress() != null;
    }

    /**
     * Helper method checking if the Receiver's address has been set by ledger
     * @param receiver
     * @return whether receiver is valid
     */
    public boolean isReceiverValid(Account receiver){
        return receiver != null && receiver.getAddress() != null;
    }

    public void setTransactionOrderIdentifier(String transactionOrderIdentifier) {
        this.transactionOrderIdentifier = transactionOrderIdentifier;
    }

    public String getTransactionOrderIdentifier() {
        return transactionOrderIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                fee == that.fee &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(transactionOrderIdentifier, that.transactionOrderIdentifier) &&
                Objects.equals(payload, that.payload) &&
                Objects.equals(receiver, that.receiver) &&
                Objects.equals(payer, that.payer) &&
                Objects.equals(logger, that.logger);
    }

    @Override
    public int hashCode() {

        return Objects.hash(transactionId, transactionOrderIdentifier, amount, fee, payload, receiver, payer, logger);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", payload='" + payload + '\'' +
                ", receiver=" + receiver +
                ", payer=" + payer +
                '}';
    }
}
