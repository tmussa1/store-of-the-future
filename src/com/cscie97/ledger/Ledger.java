package com.cscie97.ledger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
   * @author - Tofik Mussa
   * This is the heart of the BlockchainLedgerSystem responsible for creating accounts, processing transactions, maintaining
   * balances, committing blocks and performing validation
*/
public class Ledger {

    private String name;
    private String description;
    private String seed;
    private Block genesisBlock;
    private Map<Integer, Block> blockMap;

    /**
     * During ledger initialization through the constructor, an account with address "master" is created with all of the
     * available funds. The genesisBlock is also created with a blockNumber of 1 and it's previousHash field is set to null
     * It  was not obvious at first but king a new instance of the HashMap in the block's constructor always
     * resetted the accountBalanceMap and resulted in NullPointerException when trying to access accounts.
     * Therefore, the genesis block gets a new hash map during initialization
     * @param name
     * @param seed
     * @param description
     * @throws LedgerException
     */
    public Ledger(String name, String description, String seed) throws LedgerException {
        this.name = name;
        this.description = description;
        this.seed = seed;
        this.blockMap = new HashMap<>();
        this.genesisBlock = new Block(null);
        this.genesisBlock.setBlockNumber(1);
        this.genesisBlock.setAccountBalanceMap(new HashMap<String, Account>());
        blockMap.put(genesisBlock.getBlockNumber(), genesisBlock);
        initializeMasterAccountAndAddItToGenesis("master", new Account("master"));
    }

    /**
     * This creates an account if it's address attribute doesn't exist in the current block's accountBalanceMap already
     * and it sets the account's initial balance to 0 with the exception of the master account
     * which is created with all of the funds available.
     * @param address
     * @throws LedgerException
     * @return an account
     */
    public Account createAccount(String address) throws LedgerException {
        verifyIfAccountAddressExists(address);
        Account newAccount = new Account(address);
        createNonMasterAccountAndAddItToCurrentBlock(address, newAccount);
        return newAccount;
    }

    /**
     * Helper method to add a master account to the genesis block's accountBalanceMap
     * @param masterAccount
     * @param address
     * @throws LedgerException
     */
    private void initializeMasterAccountAndAddItToGenesis(String address, Account masterAccount) throws LedgerException {
            masterAccount.setBalance(Integer.MAX_VALUE);
            this.genesisBlock.addsNewAccountToAccountBalanceMap(masterAccount);
    }

    /**
     * Helper method to add a non-master account to the current block's accountBalanceMap
     * @param address
     * @param newAccount
     */
    private void createNonMasterAccountAndAddItToCurrentBlock(String address, Account newAccount) {
        if(!address.equals("master")){
            blockMap.get(blockMap.size()).getAccountBalanceMap().put(address, newAccount);
        }
    }

    /**
     * Check's if account with the same address exists, throws LedgerException if it does
     * @param address
     * @throws LedgerException
    */
    private void verifyIfAccountAddressExists(String address) throws LedgerException {
            if(blockMap.get(blockMap.size()).getAccountBalanceMap().containsKey(address)){
                throw new LedgerException("Account can not be created", "Account address already exists");
            }
    }

    /**
     * Gets current block since the block numbers are committed in increments of 1.A new block is saved for every 10
     * transaction in the helper method addTransactionToABlock(Block, Transaction) : void(). If payer and receiver are
     * not linked to valid accounts in the last completed block, throw ledger exception. Transaction Id is set when
     * instantiating a Transaction object. If any of the validation fails for creating a transaction in the Transaction
     * class, the transaction Id will be null throwing ledger exception. If validation passes, it returns the transaction
     * identifier.
     * @param transaction
     * @return transaction identifier
     * @throws LedgerException
     */
    public String processTransaction(Transaction transaction) throws LedgerException {

        Block currentBlock = blockMap.get(blockMap.size());

        if(payerOrReceiverAreNotFound(currentBlock,transaction)){
            throw new LedgerException("Transaction can not be processed", "Payer or Receiver not found");
        }

        if(transaction.getTransactionId() != null){
            addTransactionToABlock(currentBlock, transaction);
        } else {
            throw new LedgerException("Transaction can not be processed", "Invalid transaction");
        }

        return transaction.getTransactionId();
    }

    /**
     * Helper method to check if payer and receiver are linked to valid accounts in the last completed block
     * @param currentBlock
     * @param transaction
     * @return whether payer and receiver exist
     */
    private boolean payerOrReceiverAreNotFound(Block currentBlock, Transaction transaction){
        return findPayer(currentBlock, transaction) == null
                || findReceiver(currentBlock, transaction) == null;
    }

    /**
     * Helper method to check if payer is linked to a valid account in the last completed block
     * @param transaction
     * @param currentBlock
     * @return an payer account
     */
    public Account findPayer(Block currentBlock, Transaction transaction){
        if(transaction.getPayer() != null){
            return currentBlock.getAccountBalanceMap().get(transaction.getPayer().getAddress());
        }
        return null;
    }

    /**
     * Finds account by address in the current block
     * @param address
     * @return - an account
     */
    public Account getAccountByAddress(String address){
        return  blockMap.get(blockMap.size()).getAccountBalanceMap().get(address);
    }

    /**
     * Helper method to check if receiver is linked to a valid account in the last completed block
     * @param transaction
     * @param currentBlock
     * @return a receiver account
     */
    public Account findReceiver(Block currentBlock, Transaction transaction){
        if(transaction.getReceiver() != null){
            return currentBlock.getAccountBalanceMap().get(transaction.getReceiver().getAddress());
        }
        return null;
    }

    /**
     * Helper method to commit transaction to a block. Checks if the current block has not reached the transaction limit
     * of 10. Checks if a transaction with the same Id exists in the current block and adds it to the block if it doesn't.
     * Throw exception for duplicate transaction id. Increase the master's account balance with the fee paid. Also
     * decrease the payer's balance with fee and amount if payer is not master. Decreases the payer's balance by the
     * amount paid to receiver and fee paid to master. Decrease the master's balance just by amount. Increase the
     * receiver's balance by the amount received
     * If adding this transaction resulted in reaching the maximum transaction limit of 10 for the current
     * block: check if the sum of balances of all accounts in the current block is equal to the master's balance
     * during initialization(Throw exception if it doesn't) generate the hash for current block create a
     * new block setting it's previousHash property to the currentblock's hash. Copy the accountBalanceMap
     * to the newly created block increment the newly created block's blockNumber property during instantiation
     * and save it to the blockMap
     * @param currentBlock
     * @param transaction
     * @return current block's hash
     * @throws LedgerException
     */
    private String addTransactionToABlock(Block currentBlock, Transaction transaction) throws LedgerException {

        if(currentBlock.isTransactionSizeUnderLimit()){
            if(doesTransactionAlreadyExist(currentBlock, transaction).isEmpty()){
                currentBlock.addTransaction(transaction);
            } else {
                throw new LedgerException("Transaction can not be processed",
                        "Transaction has been submitted for processing before");
            }

            if(!transaction.getPayer().getAddress().equals("master")){
                currentBlock.increaseAccountBalance("master", transaction.getFee());
                currentBlock.decreaseAccountBalance(transaction.getPayer().getAddress(),
                        transaction.getAmount() + transaction.getFee());
            } else {
                currentBlock.decreaseAccountBalance(transaction.getPayer().getAddress(),
                        transaction.getAmount());
            }
            currentBlock.increaseAccountBalance(transaction.getReceiver().getAddress(),
                    transaction.getAmount());
        }
        if(currentBlock.getTransactions().size() > 10){
            checkAccountBalanceOfSavedBlock(currentBlock);
            currentBlock.setHash(currentBlock.generateHash(getSeed()));
            Block nextBlock = new Block(currentBlock.getHash());
            nextBlock.setAccountBalanceMap(currentBlock.getAccountBalanceMap());
            this.blockMap.put(nextBlock.getBlockNumber(), nextBlock);
        }

        return currentBlock.getHash();
    }

    /**
     * When the transaction size reaches 10 for a block, the account balances are recomputed. If the balance doesn't add
     * up to the master account's balance during initialization, an exception is thrown. The Hash for current block won't be computed
     * and the ledger won't be moving to create the next block
     * @param currentBlock
     * @throws LedgerException
     */
    private void checkAccountBalanceOfSavedBlock(Block currentBlock) throws LedgerException {
        if(sumOfAccountBalances(currentBlock) != Integer.MAX_VALUE){
            throw new LedgerException("Block can not be saved", "Account balances should add up to 2147483647");
        }
    }

    /**
     * Helper method to sum account balances of the current block
     * @param currentBlock
     * @return sum of balances
     */
    private int sumOfAccountBalances(Block currentBlock){
        return currentBlock.getAccountBalanceMap().values().stream()
                .map(account -> account.getBalance())
                .reduce(0, (a, b) -> a + b);
    }

    /**
     * Helper method to check if a transaction with the same Id exists in the current block.
     * This enforces idempotency
     * @param currentBlock
     * @param transaction
     * @return transaction if it exists
     */
    private Optional<Transaction> doesTransactionAlreadyExist(Block currentBlock, Transaction transaction){
        return currentBlock.getTransactions().stream()
                .filter(aTransaction -> aTransaction.getTransactionId().equals(transaction.getTransactionId()))
                .findFirst();
    }

    /**
     * Gets account balance for a single account
     * @param address
     * @return account balance for a single account
     */
    public int getAccountBalance(String address){
        return blockMap.get(blockMap.size()).getAccountBalanceMap().get(address).getBalance();
    }

    /**
     * Gets Account balance for every address that is on the block
     * @return account balance map for all accounts
     */
    public Map<String, Integer> getAccountBalances(){
        Map<String, Integer> addressBalanceMap = new HashMap<>();
        for(Map.Entry<String, Account> accountEntry : blockMap.get(blockMap.size()).getAccountBalanceMap().entrySet()){
            addressBalanceMap.put(accountEntry.getKey(), accountEntry.getValue().getBalance());
        }
        return addressBalanceMap;
    }

    /**
     * Gets transaction by transaction order identifier, this is different from the UUID that gets
     * generated when a transaction is successful
     * @param transactionOrderIdentifier
     * @return a transaction if present
     */
    public Optional<Transaction> getTransaction(String transactionOrderIdentifier){
        return blockMap.get(blockMap.size()).getTransactions().stream()
                .filter(transaction -> transaction.getTransactionOrderIdentifier().equals(transactionOrderIdentifier))
                .findFirst();
    }

    /**
     * Gets all of the transactions for the current block
     * @return list of transactions in current block
     */
    public List<Transaction> getAllTransactionsForTheCurrentBlock(){
        return blockMap.get(blockMap.size()).getTransactions();
    }

    /**
     * Gets a block in the ledger by blockNumber
     * @param blockNumber
     * @return lookup a block by block number
     */
    public Block getBlock(int blockNumber){
        return this.blockMap.get(blockNumber);
    }

    /**
     * Gets total number of blocks
     * @return total number of blocks
     */
    public int getTotalNumberOfBlocks(){
        return blockMap.size();
    }

    /**
     * gets current block
     * @return current block
     */
    public Block getCurrentBlock(){
        return this.blockMap.get(blockMap.size());
    }

    public String getSeed() {
        return seed;
    }

    /**
     * The validate method checks hash consistency across the block and that the balances add up to master's balance
     * during initialization for each block. The helper method checkAccountBalanceOfSavedBlock(Block) gets called
     * for each block and throws exception for balance discrepancies. checkIfTheHashesAreConsistent(Block, int)
     * also recomputes the hash of each block and compares it to the previousHash field of the next block throwing
     * exception if they are not equal. It also checks that the number of transactions in each saved block is 10.
     * The reason why iterating 1 less than the number of blocks in the Ledger (i < blockMap.size()) is that I am not
     * doing validation for the last block until all of the 10 transactions are committed and the hash is generated.
     * This might be a deviation from the design document where the block is added to blockMap after 10 transactions
     * are committed. I added the block to the blockMap upon creation but delayed generating the hash until after 10
     * transactions are committed. If I do validation for the last block, it doesn't have a hash yet but I can still
     * validate using it's previousHash field. I am also not doing the transaction size validation for the last block
     * because it may not have 10 transactions yet at the time of validation
     * @throws LedgerException
     */
    public void validate() throws LedgerException {
        for(int i = 1; i < blockMap.size(); i++){
            Block block = blockMap.get(i);
            boolean consistentHash = checkIfTheHashesAreConsistent(block, i);
            if(!consistentHash){
                throw new LedgerException("Validation failed", "Inconsistent hashing across the block");
            }
            checkAccountBalanceOfSavedBlock(block);
            checkTheNumberOfTransactionsInEachBlockIsTen(block);
        }


    }

    /**
     *@throws LedgerException if the number of transactions for a saved block is not equal to 10
     */
    private void checkTheNumberOfTransactionsInEachBlockIsTen(Block block) throws LedgerException {
        if(block.getTransactions().size() != 10){
            throw new LedgerException("Validation failed", "Each block must have 10 transactions");
        }
    }

    /**
     * Checks hash consistency by generating a hash of the current block and comparing it to the previousHash
     * field of the next block
     * @param block
     * @param index
     * @return whether the hashes are consistent
     */
    private boolean checkIfTheHashesAreConsistent(Block block, int index) {
        if(!block.generateHash(getSeed()).equals(blockMap.get(index + 1).getPreviousHash())){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", seed='" + seed + '\'' +
                ", genesisBlock=" + genesisBlock +
                ", blockMap=" + blockMap +
                '}';
    }

}
