package com.cscie97.store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {

    private String basketId;
    private Map<Product, Integer> productsMap;

    public Basket(String basketId) {
        this.basketId = basketId;
        this.productsMap = new HashMap<>();
    }

    /**
     *
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
