package com.example.fixture.service;

import com.example.fixture.model.OrderItem;
import com.example.fixture.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Coordinates inventory checks through the repository interface. */
public final class InventoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public void reserve(OrderItem item) {
        LOGGER.trace("Checking inventory for sku={} requested={}", item.sku(), item.quantity());
        int available = repository.quantityFor(item.sku());
        LOGGER.debug("Inventory lookup sku={} available={}", item.sku(), available);

        if (available < item.quantity()) {
            LOGGER.warn("Insufficient inventory sku={} requested={} available={}",
                    item.sku(), item.quantity(), available);
            throw new InsufficientInventoryException(
                    "Not enough inventory for " + item.sku() + ": requested=" + item.quantity()
                            + ", available=" + available
            );
        }

        repository.save(item.sku(), available - item.quantity());
        LOGGER.info("Reserved inventory sku={} quantity={} remaining={}",
                item.sku(), item.quantity(), available - item.quantity());
    }
}
