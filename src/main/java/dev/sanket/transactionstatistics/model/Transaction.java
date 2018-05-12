package dev.sanket.transactionstatistics.model;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Transaction {

    private final String id = UUID.randomUUID().toString();

    private double amount;

    private long timestamp;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}
