package dev.sanket.transactionstatistics.util;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionRepositoryCleaner {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepositoryCleaner.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(initialDelay = 60000, fixedRate = 1000)
    public void purgeTransactions() {

        logger.debug("Purging transactions older than 60 seconds");

        int expiredSecond = LocalTime.now().getSecond();
        transactionRepository.cleanUpTransactionsForSecond(expiredSecond);
    }

}
