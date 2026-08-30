package com.example.fixture.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Thread-safe in-memory implementation used by the sample app and tests. */
public final class InMemoryInventoryRepository implements InventoryRepository {
    private final Map<String, Integer> quantities = new ConcurrentHashMap<>();

    @Override
    public int quantityFor(String sku) {
        return quantities.getOrDefault(sku, 0);
    }

    @Override
    public void save(String sku, int quantity) {
        quantities.put(sku, quantity);
    }
}
