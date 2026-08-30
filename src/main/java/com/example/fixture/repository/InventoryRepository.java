package com.example.fixture.repository;

/** Abstraction implemented by {@link InMemoryInventoryRepository}. */
public interface InventoryRepository {
    int quantityFor(String sku);

    void save(String sku, int quantity);
}
