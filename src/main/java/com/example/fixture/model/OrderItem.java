package com.example.fixture.model;

import org.apache.commons.lang3.StringUtils;

/** A line item in an {@link Order}. */
public record OrderItem(String sku, int quantity, double unitPrice) {
    public OrderItem {
        if (StringUtils.isBlank(sku)) {
            throw new IllegalArgumentException("SKU must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must not be negative");
        }
    }

    public double subtotal() {
        return quantity * unitPrice;
    }
}
