package dev.sanket.transactionstatistics.service;

import dev.sanket.transactionstatistics.exception.TransactionServiceException;
import dev.sanket.transactionstatistics.model.Transaction;

public interface TransactionService {

    public void add(Transaction transaction) throws TransactionServiceException;
}
