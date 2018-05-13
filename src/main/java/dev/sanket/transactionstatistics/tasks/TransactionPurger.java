package dev.sanket.transactionstatistics.tasks;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.sanket.transactionstatistics.service.StatisticsService;

@Component
public class TransactionPurger {

    private static final Logger logger = LoggerFactory.getLogger(TransactionPurger.class);

    @Autowired
    private StatisticsService statisticsService;

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void purgeTransactions() {

        logger.debug("Purging transactions older than 60 seconds");

        int expiredSecond = LocalTime.now().getSecond();
        this.statisticsService.purgeStatisticsFor(expiredSecond);
    }

}
