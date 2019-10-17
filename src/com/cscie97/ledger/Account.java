package com.cscie97.ledger;

import java.io.Serializable;
import java.util.Objects;

/**
    * @author Tofik Mussa
    * This class is referenced in the block class. It's instance is created, address duplication is checked and balances
    * are maintained by the ledger
*/
public class Account implements Serializable {

    private String address;
    private int balance;

    /**
     * A unique address is set to account during initialization. The ledger checks for duplicate addresses before
     * initialization. No setters for address and hence it is immutable once Account is instantiated. The balance
     * for every account during creation is zero. The ledger sets it to Integer.MAX_VALUE if the unique address
     * that was passed in was master.
     * @param address
     */
    public Account(String address) {
        this.address = address;
        this.balance = 0;
    }

    public String getAddress() {
        return address;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Checks if balance is in the threshold and updates.
     * @param balance
     * @throws LedgerException if balance is not in range
     */
    public void setBalance(int balance) throws LedgerException {
        if (isValidBalance(balance)) {
            this.balance = balance;
        } else {
            throw new LedgerException("Balance can not be set", "Balance must be between the range of 0 and 2147483647");
        }
    }

    /**
    * Helper method to check if balance is in the threshold
    * @param balance
     */
    private boolean isValidBalance(int balance) {
        return balance >= 0 && balance <= Integer.MAX_VALUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return balance == account.balance &&
                Objects.equals(address, account.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }
}

