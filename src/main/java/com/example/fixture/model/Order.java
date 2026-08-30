package com.example.fixture.model;

import java.util.List;

/** An order whose item type is declared in another source file. */
public record Order(String id, String customerName, List<OrderItem> items) {
    public Order {
        items = List.copyOf(items);
    }

    public double total() {
        return items.stream().mapToDouble(OrderItem::subtotal).sum();
    }
}
