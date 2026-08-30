package com.example.fixture.util;

import com.example.fixture.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** Serializes the app's model with the public Gson library. */
public final class ReceiptFormatter {
    private final Gson gson;

    public ReceiptFormatter() {
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public String toJson(Order order) {
        return gson.toJson(order);
    }
}
