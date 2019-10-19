package com.cscie97.store.model;

/**
 * Products across all of the stores
 * @author Tofik Mussa
 */
public class Product {

    private String productId;
    private String productName;
    private String productDescription;
    private String category;
    private int price;
    private double volume;
    private Temperature temperature;

    /**
     *
     * @param productId
     * @param productName
     * @param productDescription
     * @param category
     * @param price
     * @param volume
     * @param temperature
     */
    public Product(String productId, String productName, String productDescription, String category, int price,
                   double volume, Temperature temperature) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;
        this.volume = volume;
        this.temperature = temperature;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getVolume() {
        return volume;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", temperature=" + temperature +
                '}';
    }
}
