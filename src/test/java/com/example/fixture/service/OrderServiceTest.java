package com.example.fixture.service;

import com.example.fixture.model.Order;
import com.example.fixture.model.OrderItem;
import com.example.fixture.repository.InMemoryInventoryRepository;
import com.example.fixture.util.ReceiptFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceTest {
    private InMemoryInventoryRepository repository;
    private OrderService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryInventoryRepository();
        service = new OrderService(new InventoryService(repository), new ReceiptFormatter());
    }

    @Test
    void acceptsAnOrderWhenInventoryIsAvailable() {
        repository.save("TEST-SKU", 2);
        Order order = new Order("TEST-1", "Test Customer",
                List.of(new OrderItem("TEST-SKU", 1, 10.00)));

        assertTrue(service.process(order));
    }

    @Test
    void defersAnOrderWhenInventoryIsUnavailable() {
        Order order = new Order("TEST-2", "Test Customer",
                List.of(new OrderItem("MISSING-SKU", 1, 10.00)));

        assertFalse(service.process(order));
    }
}
