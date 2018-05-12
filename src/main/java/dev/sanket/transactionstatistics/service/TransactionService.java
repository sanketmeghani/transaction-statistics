package dev.sanket.transactionstatistics.service;

import javax.transaction.TransactionRequiredException;

import dev.sanket.transactionstatistics.model.Transaction;

public interface TransactionService {

    public void add(Transaction transaction) throws TransactionRequiredException;
}
