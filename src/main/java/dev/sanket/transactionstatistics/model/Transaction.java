package dev.sanket.transactionstatistics.model;

import java.util.UUID;

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

        StringBuilder sb = new StringBuilder();

        sb.append("{").append(id).append(", ").append(amount).append(",").append(timestamp).append("}");

        return sb.toString();
    }
}
