package dev.sanket.transactionstatistics.service;

import java.util.Map;

import dev.sanket.transactionstatistics.model.Transaction;

public interface StatisticsService {

    public void trackTransaction(Transaction transaction);

    public Map<String, Object> getStatistics();

    public void purgeStatisticsFor(int second);
}
