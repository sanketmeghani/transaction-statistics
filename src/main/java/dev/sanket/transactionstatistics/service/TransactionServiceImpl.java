package dev.sanket.transactionstatistics.service;

import org.springframework.beans.factory.annotation.Autowired;

import dev.sanket.transactionstatistics.exception.TransactionServiceException;
import dev.sanket.transactionstatistics.model.Transaction;
import dev.sanket.transactionstatistics.util.TransactionRepository;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void add(Transaction transaction) throws TransactionServiceException {

        long transactionAge = (System.currentTimeMillis() - transaction.getTimestamp()) / 1000;

        if (transactionAge > 60) {
            throw new TransactionServiceException("Transaction is older than 60 seconds - " + transaction);
        }

        int absoluteSeconds = (int) ((transaction.getTimestamp() / 1000) % 60);
        transactionRepository.put(absoluteSeconds, transaction);
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
