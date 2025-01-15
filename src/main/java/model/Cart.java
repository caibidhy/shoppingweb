package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, Integer> items; // productId -> quantity

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(int productId, int quantity) {
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
    }

    public void removeItem(int productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public Map<Integer, Integer> getItems() {
        return new HashMap<>(items);
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = new HashMap<>(items);
    }

    public int getQuantity(int productId) {
        return items.getOrDefault(productId, 0);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
}

