package com.example.fixture;

import com.example.fixture.config.ApplicationConfig;
import com.example.fixture.model.Order;
import com.example.fixture.model.OrderItem;
import com.example.fixture.repository.InMemoryInventoryRepository;
import com.example.fixture.service.InventoryService;
import com.example.fixture.service.OrderService;
import com.example.fixture.util.ReceiptFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/** Entry point that wires the sample application's collaborating classes together. */
public final class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    public static void main(String[] args) {
        ApplicationConfig config = ApplicationConfig.fromEnvironment();
        LOGGER.info("Starting {} in {} mode", config.applicationName(), config.environment());

        InMemoryInventoryRepository repository = new InMemoryInventoryRepository();
        repository.save("JAVA-BOOK", 5);
        repository.save("INDEX-CARD", 0);

        InventoryService inventoryService = new InventoryService(repository);
        ReceiptFormatter receiptFormatter = new ReceiptFormatter();
        OrderService orderService = new OrderService(inventoryService, receiptFormatter);

        Order accepted = new Order(
                "ORDER-1001",
                "Ada Lovelace",
                List.of(new OrderItem("JAVA-BOOK", 2, 39.95))
        );
        Order rejected = new Order(
                "ORDER-1002",
                "Grace Hopper",
                List.of(new OrderItem("INDEX-CARD", 3, 4.50))
        );

        orderService.process(accepted);
        orderService.process(rejected);
        orderService.logExpectedFailure("Demonstration error for log indexer validation");

        LOGGER.info("Sample run complete; generated TRACE, DEBUG, INFO, WARN, and ERROR events");
    }
}
