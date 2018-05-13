package dev.sanket.transactionstatistics.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.sanket.transactionstatistics.model.Statistics;
import dev.sanket.transactionstatistics.model.Transaction;

public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private Statistics globalStats = new Statistics();

    private ConcurrentHashMap<Integer, Statistics> secondWiseStats = new ConcurrentHashMap<>();

    public StatisticsServiceImpl() {
        IntStream.range(0, 60).forEach((second) -> secondWiseStats.put(Integer.valueOf(second), new Statistics()));
    }

    @Override
    public void trackTransaction(Transaction transaction) {

        ZonedDateTime transactionTime = Instant.ofEpochMilli(transaction.getTimestamp()).atZone(ZoneId.systemDefault());
        int absoluteSecond = transactionTime.getSecond();
        double transactionAmount = transaction.getAmount();

        logger.debug("Transaction statistics bucket - {}", absoluteSecond);
        logger.debug("Current statistics - {}", this.globalStats);

        Statistics statistics = secondWiseStats.get(Integer.valueOf(absoluteSecond));

        this.globalStats.lockForWrite();

        statistics.addNewTransaction(transactionAmount);
        this.globalStats.addNewTransaction(transactionAmount);

        this.globalStats.releaseWriteLock();

        logger.debug("Updated statistics - {}", this.globalStats);
    }

    @Override
    public Map<String, Object> getStatistics() {
        return globalStats.snapshot();
    }

    @Override
    public void purgeStatisticsFor(int second) {

        Statistics statisticsToBePurged = this.secondWiseStats.get(Integer.valueOf(second));

        this.globalStats.lockForWrite();
        statisticsToBePurged.lockForWrite();

        logger.debug("Purging statistics for second - {}", second);
        logger.debug("Current statistics - {}", this.globalStats);
        logger.debug("Purging statistics - {}", statisticsToBePurged);

        statisticsToBePurged.reset();
        statisticsToBePurged.releaseWriteLock();

        updateGlobalStats(second);

        this.globalStats.releaseWriteLock();
        logger.debug("Updated statistics - {}", this.globalStats);
    }

    private void updateGlobalStats(int purgedBucket) {

        this.globalStats.reset();

        double max = this.globalStats.getMax();
        double min = this.globalStats.getMin();
        double sum = this.globalStats.getSum();
        double avg = this.globalStats.getAvg();
        long count = this.globalStats.getCount();

        for (int second = 0; second < 60; second++) {

            if (second == purgedBucket) {
                continue;
            }

            Statistics statistics = this.secondWiseStats.get(Integer.valueOf(second));

            if (count == 0) {
                max = statistics.getMax();
                min = statistics.getMin();
            } else if (statistics.getMax() > max && statistics.getMax() != 0) {
                max = statistics.getMax();
            } else if (statistics.getMin() < min && statistics.getMin() != 0) {
                min = statistics.getMin();
            }

            count = count + statistics.getCount();
            sum = sum + statistics.getSum();
        }

        if (count > 0) {
            avg = sum / count;
        }

        this.globalStats.setMax(max);
        this.globalStats.setMin(min);
        this.globalStats.setCount(count);
        this.globalStats.setSum(sum);
        this.globalStats.setAvg(avg);
    }
}
