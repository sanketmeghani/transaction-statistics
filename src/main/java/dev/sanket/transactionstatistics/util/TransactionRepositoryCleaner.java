package dev.sanket.transactionstatistics.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionRepositoryCleaner {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRepositoryCleaner.class);

    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    public void purgeTransactions() {
        logger.debug("Cleaning transactions older than 60 seconds");
    }

}
