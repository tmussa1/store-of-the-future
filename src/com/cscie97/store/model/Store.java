package com.cscie97.store.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tofik Mussa
 */
public class Store {

    private String storeId;
    private String storeName;
    private Address address;
    private List<Aisle> aisles;

    public Store(String storeId, String storeName, Address address) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.aisles = new ArrayList<>();
    }

    public void addAisleToAStore(Aisle aisle){
        this.aisles.add(aisle);
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public Address getAddress() {
        return address;
    }

    public List<Aisle> getAisles() {
        return aisles;
    }
}
