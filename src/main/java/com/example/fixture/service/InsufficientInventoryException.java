package com.example.fixture.service;

/** Domain exception linking {@link OrderService} and {@link InventoryService}. */
public final class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}
