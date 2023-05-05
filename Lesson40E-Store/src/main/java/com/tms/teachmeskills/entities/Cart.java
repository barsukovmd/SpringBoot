package com.tms.teachmeskills.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Cart {

    private Map<Integer, Product> productMap;
    private int totalPrice;

    public Cart() {
        this.productMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        productMap.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(Product product) {
        productMap.remove(product.getId(), product);
        totalPrice -= product.getPrice();
    }

    public List<Product> productList(Product product) {
        return new ArrayList<>(productMap.values());
    }

    public void clear() {
        productMap.clear();
    }
}
