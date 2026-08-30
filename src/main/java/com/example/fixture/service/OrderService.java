package com.example.fixture.service;

import com.example.fixture.model.Order;
import com.example.fixture.model.OrderItem;
import com.example.fixture.util.ReceiptFormatter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Processes orders and intentionally produces varied log events. */
public final class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final InventoryService inventoryService;
    private final ReceiptFormatter receiptFormatter;

    public OrderService(InventoryService inventoryService, ReceiptFormatter receiptFormatter) {
        this.inventoryService = inventoryService;
        this.receiptFormatter = receiptFormatter;
    }

    public boolean process(Order order) {
        LOGGER.trace("Entered OrderService.process orderId={}", order.id());

        if (StringUtils.isBlank(order.customerName())) {
            LOGGER.error("Rejected orderId={} because customer name is blank", order.id());
            return false;
        }

        LOGGER.debug("Processing orderId={} itemCount={} total={}",
                order.id(), order.items().size(), order.total());
        try {
            for (OrderItem item : order.items()) {
                inventoryService.reserve(item);
            }
            LOGGER.info("Order accepted receipt={}", receiptFormatter.toJson(order));
            return true;
        } catch (InsufficientInventoryException exception) {
            LOGGER.warn("Order deferred orderId={} reason={}", order.id(), exception.getMessage());
            return false;
        }
    }

    public void logExpectedFailure(String message) {
        LOGGER.error("Expected sample failure: {}", message);
    }
}
