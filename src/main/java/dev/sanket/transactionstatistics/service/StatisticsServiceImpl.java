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
        logger.debug("Current statistics - {}", globalStats);

        Statistics statistics = secondWiseStats.get(Integer.valueOf(absoluteSecond));

        globalStats.lockForWrite();

        statistics.addNewTransaction(transactionAmount);
        globalStats.addNewTransaction(transactionAmount);

        globalStats.releaseWriteLock();

        logger.debug("Updated statistics - {}", globalStats);
    }

    @Override
    public Map<String, Object> getStatistics() {
        return globalStats.snapshot();
    }

    @Override
    public void purgeStatisticsFor(int second) {

        Statistics statisticsToBePurged = this.secondWiseStats.get(Integer.valueOf(second));

        globalStats.lockForWrite();
        statisticsToBePurged.lockForWrite();

        logger.debug("Purging statistics for second - {}", second);
        logger.debug("Current statistics - {}", globalStats);
        logger.debug("Purging statistics - {}", statisticsToBePurged);

        statisticsToBePurged.reset();
        statisticsToBePurged.releaseWriteLock();

        updateGlobalStats();

        globalStats.releaseWriteLock();
        logger.debug("Updated statistics - {}", globalStats);
    }

    private void updateGlobalStats() {

        globalStats.reset();

        double max = globalStats.getMax();
        double min = globalStats.getMin();
        double sum = globalStats.getSum();
        double avg = globalStats.getAvg();
        long count = globalStats.getCount();

        for (Statistics statistics : this.secondWiseStats.values()) {

            if (count == 0) {
                max = statistics.getMax();
                min = statistics.getMin();
            } else if (statistics.getMax() > max) {
                max = statistics.getMax();
            } else if (statistics.getMin() < min) {
                min = statistics.getMin();
            }

            count = count + statistics.getCount();
            sum = sum + statistics.getSum();
        }

        if (count > 0) {
            avg = sum / count;
        }

        globalStats.setMax(max);
        globalStats.setMin(min);
        globalStats.setCount(count);
        globalStats.setSum(sum);
        globalStats.setAvg(avg);
    }
}
