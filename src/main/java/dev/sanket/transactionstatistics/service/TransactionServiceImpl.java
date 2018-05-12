package dev.sanket.transactionstatistics.service;

import javax.transaction.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;

import dev.sanket.transactionstatistics.model.Transaction;
import dev.sanket.transactionstatistics.util.TransactionRepository;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void add(Transaction transaction) throws TransactionRequiredException {

        long transactionAge = (System.currentTimeMillis() - transaction.getTimestamp()) / 1000;

        if (transactionAge > 60) {
            throw new TransactionRequiredException("Transaction is older than 60 seconds - " + transaction);
        }

        transactionRepository.put((int) transactionAge, transaction);
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
