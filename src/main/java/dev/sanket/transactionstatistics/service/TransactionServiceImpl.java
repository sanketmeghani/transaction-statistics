package dev.sanket.transactionstatistics.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import dev.sanket.transactionstatistics.exception.TransactionServiceException;
import dev.sanket.transactionstatistics.model.Transaction;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private StatisticsService statisticsService;

    @Override
    public void add(Transaction transaction) throws TransactionServiceException {

        long transactionAge = (System.currentTimeMillis() - transaction.getTimestamp()) / 1000;

        if (transactionAge > 60) {
            
            ZonedDateTime transactionTime = Instant.ofEpochMilli(transaction.getTimestamp())
                    .atZone(ZoneId.systemDefault());
            throw new TransactionServiceException(
                    "Transaction is older than 60 seconds. Transaction time - " + transactionTime);
        }

        this.statisticsService.trackTransaction(transaction);
    }
}
