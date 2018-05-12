package dev.sanket.transactionstatistics.service;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.sanket.transactionstatistics.model.Statistics;
import dev.sanket.transactionstatistics.model.Transaction;

public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private Statistics statistics = new Statistics();

    private ReentrantReadWriteLock statisticsLock = new ReentrantReadWriteLock();

    @Override
    public void trackTransaction(Transaction transaction) {

        logger.debug("Updating statistics for transaction - {}", transaction);
        updateStatistics(transaction.getAmount());
        logger.debug("Updated statistics - {}", statistics);
    }

    private void updateStatistics(double transactionAmount) {

        WriteLock writeStatisticsLock = statisticsLock.writeLock();

        try {
            writeStatisticsLock.lock();

            if (transactionAmount > statistics.getMax() || statistics.getCount() == 0) {
                statistics.setMax(transactionAmount);
            } else if (transactionAmount < statistics.getMin() || statistics.getCount() == 0) {
                statistics.setMin(transactionAmount);
            }

            double sum = statistics.getSum() + transactionAmount;
            long count = statistics.getCount() + 1;
            double avg = sum / count;

            statistics.setSum(sum);
            statistics.setAvg(avg);
            statistics.setCount(count);
        } finally {
            writeStatisticsLock.unlock();
        }

    }

    @Override
    public Statistics getStatistics() {

        ReadLock readStatisticsLock = statisticsLock.readLock();

        try {
            readStatisticsLock.lock();
            return statistics;
        } finally {
            readStatisticsLock.unlock();
        }
    }
}
