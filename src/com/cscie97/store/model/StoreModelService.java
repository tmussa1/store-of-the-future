package com.cscie97.store.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Tofik Mussa
 */

public class StoreModelService implements IStoreModelService {

    private List<Customer> customers;
    private List<Store> stores;
    private Map<String, Inventory> inventoryMap;
    private Map<String, Product> productMap;
    private static StoreModelService instance;

    private StoreModelService() {
        this.customers = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.inventoryMap = new HashMap<>();
        this.productMap = new HashMap<>();
    }

    public static StoreModelService getInstance(){
        if(instance == null){
            instance = new StoreModelService();
        }
        return instance;
    }

    /**
     *
     * @param storeId
     * @param storeName
     * @param storeAddress
     * @return
     * @throws StoreException
     */
    @Override
    public Store createAStore(String storeId, String storeName, Address storeAddress) throws StoreException {
        checkValidityOfStoreId(storeId);
        Store store = new Store(storeId, storeName, storeAddress);
        this.stores.add(store);
        return store;
    }

    /**
     *
     * @param storeId
     * @throws StoreException
     */
    private void checkValidityOfStoreId(String storeId) throws StoreException {
        Optional<Store> duplicateStore = this.stores.stream().filter(store -> store.getStoreId().equalsIgnoreCase(storeId))
                .findAny();
        if(!duplicateStore.isEmpty()){
            throw new StoreException("Duplicate Store with the ID provided exists. Store creation failed");
        }
    }

    /**
     *
     * @param storeId
     * @return
     * @throws StoreException
     */
    @Override
    public Store getStoreById(String storeId) throws StoreException {

        Store store = stores.stream().filter(astore -> astore.getStoreId().equalsIgnoreCase(storeId))
                .collect(Collectors.toList()).get(0);
        if(store == null){
            throw new StoreException("A store with the requested id doesn't exist");
        }
        return store;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param aisleDescription
     * @param location
     * @return
     * @throws StoreException
     */
    @Override
    public Aisle createAisle(String storeId, String aisleNumber, String aisleDescription, String location) throws StoreException {
        Store store = getStoreById(storeId);
        LocationType locationEnum = StoreUtil.convertLocationToEnum(location);
        Aisle aisle = new Aisle(aisleNumber, aisleDescription,locationEnum);
        store.addAisleToAStore(aisle);
        return aisle;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @return
     * @throws StoreException
     */
    @Override
    public Aisle getAisleByStoreIdAndAisleNumber(String storeId, String aisleNumber) throws StoreException {
        Store store = getStoreById(storeId);
        Optional<Aisle> aisle = store.getAisles().stream().filter(anAisle -> anAisle.getAisleNumber().equals(aisleNumber)).findAny();
        if(aisle.isEmpty()){
            throw new StoreException("An aisle with the requested id doesn't exist");
        }
        return aisle.get();
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param shelfName
     * @param level
     * @param shelfDescription
     * @param temperature
     * @return
     * @throws StoreException
     */
    @Override
    public Shelf createAShelf(String storeId, String aisleNumber, String shelfId, String shelfName, String level, String shelfDescription, String temperature) throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        Level levelEnum = StoreUtil.convertLevelToEnum(level);
        Temperature temperatureEnum = StoreUtil.convertTemperatureToEnum(temperature);
        Shelf shelf = new Shelf(shelfId, shelfName,levelEnum,shelfDescription,temperatureEnum);
        aisle.addShelfToAisle(shelf);
        return shelf;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @return
     * @throws StoreException
     */
    @Override
    public Shelf getShelfByStoreIdAisleNumShelfId(String storeId, String aisleNumber, String shelfId) throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        Shelf shelf = aisle.getShelves().stream().filter(aShelf -> aShelf.getShelfId().equals(shelfId)).findAny().get();
        if(shelf == null){
            throw new StoreException("A shelf with the requested id doesn't exist");
        }
        return shelf;
    }

    /**
     *
     * @param inventoryId
     * @param storeId
     * @param aisleNumber
     * @param shelfId
     * @param capacity
     * @param count
     * @param productId
     * @return
     * @throws StoreException
     */
    @Override
    public Inventory createInventory(String inventoryId, String storeId, String aisleNumber, String shelfId, int capacity, int count, String productId) throws StoreException {
        Product product = productMap.get(productId);
        if(product == null){
            throw new StoreException("Inventory can not be created for a product that is not defined");
        }
        InventoryLocation inventoryLocation = new InventoryLocation(storeId, aisleNumber, shelfId);
        Shelf shelf = getShelfByStoreIdAisleNumShelfId(storeId, aisleNumber, shelfId);
        Inventory inventory = new Inventory(inventoryId, product, count, capacity, inventoryLocation);
        inventoryMap.put(inventoryId, inventory);
        shelf.addInventoryToShelf(inventory);
        return inventory;
    }

    /**
     *
     * @param inventoryId
     * @return
     * @throws StoreException
     */
    @Override
    public Inventory getInventoryById(String inventoryId) throws StoreException {
        Inventory inventory = inventoryMap.get(inventoryId);
        if(inventory == null){
            throw new StoreException("Requested inventory not found in all of the stores");
        }
        return inventory;
    }

    /**
     * This makes sure the overall inventory map for all stores as well as the inventory in a particular shelf is updated.
     * The same inventory(same inventory id) can be split between stores so the count for a particular shelf maybe different
     * from the overall inventory count
     * @param inventoryId
     * @param difference
     * @return
     * @throws StoreException
     */
    @Override
    public int updateInventoryCount(String inventoryId, int difference) throws StoreException {
        Inventory inventoryFromAnyStore = inventoryMap.get(inventoryId);
        if(inventoryFromAnyStore == null){
            throw new StoreException("The inventory that you are trying to update the count of doesn't exist in the stores");
        }
        Shelf shelf = getShelfByStoreIdAisleNumShelfId(inventoryFromAnyStore.getInventoryLocation().getStoreId(),
                inventoryFromAnyStore.getInventoryLocation().getAisleNumber(),
                inventoryFromAnyStore.getInventoryLocation().getShelfId());
        Inventory inventoryInTheShelf = shelf.getInventoryList().stream()
                .filter(inventory -> inventory.getInventoryId().equals(inventoryId)).findAny().get();
        if(inventoryInTheShelf == null){
            throw new StoreException("The inventory is not placed in any of the shelves");
        }
        inventoryInTheShelf.setCount(inventoryInTheShelf.getCount() + difference);
        inventoryFromAnyStore.setCount(inventoryFromAnyStore.getCount() + difference);
        return inventoryFromAnyStore.getCount();
    }

    /**
     *
     * @param productId
     * @param productName
     * @param productDescription
     * @param size
     * @param category
     * @param price
     * @param temperature
     * @return
     */
    @Override
    public Product createAProduct(String productId, String productName, String productDescription, int size, String category, int price, String temperature) {
        double volume = Math.pow(size, 3);
        Product product = new Product(productId, productName, productDescription, category, price, volume,
                StoreUtil.convertTemperatureToEnum(temperature));
        this.productMap.put(productId, product);
        return product;
    }

    /**
     *
     * @param productId
     * @return
     * @throws StoreException
     */
    @Override
    public Product getProductById(String productId) throws StoreException {
        Product product = this.productMap.get(productId);
        if(product == null){
            throw new StoreException("A product with the requested id doesn't exist");
        }
        return product;
    }

    private void duplicateCustomerValidation(String customerId, String accountAddress) throws StoreException {
        Optional<Customer> duplicateCustomer = this.customers.stream()
                .filter(aCustomer -> aCustomer.getCustomerId().equals(customerId)
                        && aCustomer.getAccountAddress().equals(accountAddress))
                .findAny();
        if(!duplicateCustomer.isEmpty()){
            throw new StoreException("A customer with the same id and account address exists");
        }
    }

    /**
     *
     * @param customerId
     * @param firstName
     * @param lastName
     * @param type
     * @param emailAddress
     * @param accountAddress
     * @return
     * @throws StoreException
     */
    @Override
    public Customer createCustomer(String customerId, String firstName, String lastName, String type, String emailAddress, String accountAddress) throws StoreException {
        Customer customer = new Customer(customerId, firstName, lastName,
                StoreUtil.convertCustomerTypeToEnum(type), emailAddress, accountAddress);
        duplicateCustomerValidation(customerId, accountAddress);
        this.customers.add(customer);
        return customer;
    }

    /**
     *
     * @param customerId
     * @return
     * @throws StoreException
     */
    @Override
    public Customer getCustomerById(String customerId) throws StoreException {
        Optional<Customer> customer = this.customers.stream().filter(aCustomer ->
                aCustomer.getCustomerId().equals(customerId)).findAny();
        if(customer.isEmpty()){
            throw new StoreException("A customer with the requested id doesn't exist");
        }
        return customer.get();
    }

    /**
     *
     * @param customerId
     * @param storeId
     * @param aisleNumber
     * @return
     * @throws StoreException
     */

    @Override
    public InventoryLocation updateCustomerLocation(String customerId, String storeId, String aisleNumber) throws StoreException {
        InventoryLocation customerLocation = new InventoryLocation(storeId, aisleNumber, "");
        Customer customer = getCustomerById(customerId);
        customer.setCustomerLocation(customerLocation);
        return customer.getCustomerLocation();
    }

    /**
     *
     * @param customerId
     * @return
     * @throws StoreException
     */
    @Override
    public Basket getBasketOfACustomer(String customerId) throws StoreException {
        Customer customer = getCustomerById(customerId);
        Basket basket = customer.getBasket();
        if(basket == null){
            customer.setBasket(createBasketForACustomer(customerId, UUID.randomUUID().toString()));
        }
        this.customers.add(customer);
        return customer.getBasket();
    }

    /**
     *
     * @param customerId
     * @param basketId
     * @return
     * @throws StoreException
     */
    @Override
    public Basket createBasketForACustomer(String customerId, String basketId) throws StoreException {
        Customer customer = getCustomerById(customerId);
        Basket basket = new Basket(basketId);
        customer.setBasket(basket);
        this.customers.add(customer);
        return customer.getBasket();
    }

    /**
     *
     * @param basketId
     * @return
     * @throws StoreException
     */
    private Customer getCustomerAssociatedWithABasket(String basketId) throws StoreException {

        Customer customer = findCustomerWithBasketId(basketId);
        if(customer == null){
            throw new StoreException("There is no customer associated with this basket id");
        }
        return customer;
    }

    /**
     *
     * @param basketId
     * @return
     */
    private Customer findCustomerWithBasketId(String basketId) {
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getBasket() != null &&
                    customers.get(i).getBasket().getBasketId().equalsIgnoreCase(basketId)){
                return customers.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param productId
     * @return
     * @throws StoreException
     */
    @Override
    public Inventory getInventoryByProductId(String productId) throws StoreException {
         Inventory inventory = inventoryMap.values().stream()
                .filter(anInventory -> anInventory.getProduct().getProductId().equals(productId))
                .findAny().get();
         if(inventory == null){
             throw new StoreException("An inventory with the provided product id doesn't exist");
         }
        return inventory;
    }

    /**
     * The method that gets called to add inventory to a shelf has logic if it is existing inventory and replaces it if it is
     * @param basketId
     * @param productId
     * @param count
     * @return
     * @throws StoreException
     */
    @Override
    public Basket addItemToBasket(String basketId, String productId, int count) throws StoreException {
        Customer customer = getCustomerAssociatedWithABasket(basketId);
        Product product = this.productMap.get(productId);
        if(product == null){
            throw new StoreException("The product you are trying to pick doesn't exist");
        }
        Basket basket = customer.getBasket();
        basket.addProductToBasket(product, count);
        customer.setBasket(basket);
        Inventory inventoryOverAll = getInventoryByProductId(productId);
        inventoryOverAll.setCount(inventoryOverAll.getCount() - count);
        Shelf shelf = getShelfByStoreIdAisleNumShelfId(inventoryOverAll.getInventoryLocation().getStoreId(),
                inventoryOverAll.getInventoryLocation().getAisleNumber(),
                inventoryOverAll.getInventoryLocation().getShelfId());
        Inventory inventoryInTheShelf = shelf.getInventoryInTheShelfByInventoryId(inventoryOverAll.getInventoryId());
        inventoryInTheShelf.setCount(inventoryInTheShelf.getCount() - count);
        shelf.addInventoryToShelf(inventoryInTheShelf);
        this.inventoryMap.replace(inventoryOverAll.getInventoryId(), inventoryOverAll);
        return customer.getBasket();
    }

    /**
     * The same logic as adding. Inventory with updated count replaces existing. The basket also reflected a decreased
     * count if there are remaining items with the same product id, it gets completely removed from the basket if there
     * are not.
     * @param basketId
     * @param productId
     * @param countReturned
     * @return
     * @throws StoreException
     */
    @Override
    public Basket removeItemFromBasket(String basketId, String productId, int countReturned) throws StoreException {
        Customer customer = getCustomerAssociatedWithABasket(basketId);
        Product product = this.productMap.get(productId);
        if(product == null){
            throw new StoreException("The product you are trying to put back is not from this store");
        }
        Basket basket = customer.getBasket();
        basket.removeProductFromBasket(product, countReturned);
        customer.setBasket(basket);
        Inventory inventoryOverAll = getInventoryByProductId(productId);
        inventoryOverAll.setCount(inventoryOverAll.getCount() + countReturned);
        Shelf shelf = getShelfByStoreIdAisleNumShelfId(inventoryOverAll.getInventoryLocation().getStoreId(),
                inventoryOverAll.getInventoryLocation().getAisleNumber(),
                inventoryOverAll.getInventoryLocation().getShelfId());
        Inventory inventoryInTheShelf = shelf.getInventoryInTheShelfByInventoryId(inventoryOverAll.getInventoryId());
        inventoryInTheShelf.setCount(inventoryInTheShelf.getCount() + countReturned);
        shelf.addInventoryToShelf(inventoryInTheShelf);
        this.inventoryMap.replace(inventoryOverAll.getInventoryId(), inventoryOverAll);
        return customer.getBasket();
    }

    /**
     *
     * @param basketId
     * @return
     * @throws StoreException
     */
    @Override
    public Customer clearBasketAndRemoveAssociationWithACustomer(String basketId) throws StoreException {
        Customer customer = getCustomerAssociatedWithABasket(basketId);
        Basket basket = getBasketOfACustomer(customer.getCustomerId());
        basket.setProductsMap(null);
        customer.setBasket(null);
        return customer;
    }

    /**
     *
     * @param basketId
     * @return
     * @throws StoreException
     */
    @Override
    public Map<Product, Integer> getBasketItems(String basketId) throws StoreException {
        Customer customer = getCustomerAssociatedWithABasket(basketId);
        Basket basket = getBasketOfACustomer(customer.getCustomerId());
        return basket.getProductsMap();
    }

    /**
     *
     * @param sensorId
     * @param sensorName
     * @param sensorType
     * @param storeId
     * @param aisleNumber
     * @return
     * @throws StoreException
     */
    @Override
    public ISensor createASensor(String sensorId, String sensorName, String sensorType, String storeId,
                                 String aisleNumber) throws StoreException {
        InventoryLocation location = new InventoryLocation(storeId, aisleNumber, "");
        ISensor sensor = SensorApplianceFactory.createSensor(sensorType, sensorId, sensorName, location);
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        aisle.addSensorToShelf(sensor);
        return sensor;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param sensorId
     * @return
     * @throws StoreException
     */
    @Override
    public ISensor getSensorByLocationAndSensorId(String storeId, String aisleNumber, String sensorId)
            throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        ISensor sensor = aisle.getSensorById(sensorId);
        return sensor;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param applianceId
     * @return
     * @throws StoreException
     */
    @Override
    public IAppliance getApplianceByLocationAndSensorId(String storeId, String aisleNumber, String applianceId)
            throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        IAppliance appliance = aisle.getApplianceById(applianceId);
        return appliance;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param sensorId
     * @param event
     * @return
     * @throws StoreException
     */
    @Override
    public String createSensorEvent(String storeId, String aisleNumber, String sensorId, Event event)
            throws StoreException {
        ISensor sensor = getSensorByLocationAndSensorId(storeId, aisleNumber, sensorId);
        return sensor.generateSensorEvent(event);
    }

    /**
     *
     * @param applianceId
     * @param applianceName
     * @param applianceType
     * @param storeId
     * @param aisleNumber
     * @return
     * @throws StoreException
     */
    @Override
    public IAppliance createAnAppliance(String applianceId, String applianceName, String applianceType,
                                        String storeId, String aisleNumber) throws StoreException {
        InventoryLocation location = new InventoryLocation(storeId, aisleNumber, "");
        IAppliance appliance = SensorApplianceFactory.createAppliance(applianceType, applianceId,
                applianceName, location);
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        aisle.addApplianceToShelf(appliance);
        return appliance;
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param applianceId
     * @param event
     * @return
     * @throws StoreException
     */
    @Override
    public String createApplianceEvent(String storeId, String aisleNumber, String applianceId, Event event) throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        IAppliance appliance = aisle.getApplianceById(applianceId);
        return appliance.generateApplianceEvent(event);
    }

    /**
     *
     * @param storeId
     * @param aisleNumber
     * @param applianceId
     * @param command
     * @return
     * @throws StoreException
     */
    @Override
    public String createApplianceCommand(String storeId, String aisleNumber, String applianceId, Command command) throws StoreException {
        Aisle aisle = getAisleByStoreIdAndAisleNumber(storeId, aisleNumber);
        IAppliance appliance = aisle.getApplianceById(applianceId);
        return appliance.listenToCommand(command);
    }


}
