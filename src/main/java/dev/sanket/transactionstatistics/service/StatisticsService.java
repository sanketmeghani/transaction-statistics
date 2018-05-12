package dev.sanket.transactionstatistics.service;

import dev.sanket.transactionstatistics.model.Statistics;
import dev.sanket.transactionstatistics.model.Transaction;

public interface StatisticsService {

    public void trackTransaction(Transaction transaction);

    public Statistics getStatistics();
}
