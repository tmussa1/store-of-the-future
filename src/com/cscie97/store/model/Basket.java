package com.cscie97.store.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An association class to keep products with their counts associated with a customer
 * @author Tofik Mussa
 */
public class Basket {

    private String basketId;
    private Map<Product, Integer> productsMap;

    /**
     *
     * @param basketId
     */
    public Basket(String basketId) {
        this.basketId = basketId;
        this.productsMap = new HashMap<>();
    }

    /**
     * Adds product to a basket or increment's the count if the customer has the same item
     * @param product
     * @param quantity
     */
    public void addProductToBasket(Product product, int quantity){
        if(productsMap.containsKey(product)){
            productsMap.replace(product, productsMap.get(product) + quantity);
        } else {
            productsMap.put(product, quantity);
        }
    }

    /**
     * Removes item from basket or decrements count if the customer has an item of the same type left in basket
     * @param product
     * @param count
     */
    public void removeProductFromBasket(Product product, int count){
        if(productsMap.containsKey(product) && productsMap.get(product) == count){
            productsMap.remove(product);
        } else {
            productsMap.replace(product, productsMap.get(product) - count);
        }
    }
    public String getBasketId() {
        return basketId;
    }

    public Map<Product, Integer> getProductsMap() {
        return productsMap;
    }

    public void setProductsMap(Map<Product, Integer> productsMap) {
        this.productsMap = productsMap;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "basketId='" + basketId + '\'' +
                '}';
    }
}
