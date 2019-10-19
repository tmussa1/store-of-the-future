package com.cscie97.store.model;

/**
 * @author Tofik Mussa
 */
public class Address {

    private String street;
    private String city;
    private String state;

    /**
     *
     * @param street
     * @param city
     * @param state
     */
    public Address(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
