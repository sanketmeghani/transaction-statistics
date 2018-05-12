package dev.sanket.transactionstatistics.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import dev.sanket.transactionstatistics.model.Transaction;

public class TransactionRepository {

    private ConcurrentHashMap<Integer, List<Transaction>> transactions = new ConcurrentHashMap<Integer, List<Transaction>>();

    public TransactionRepository() {
        IntStream.range(0, 60).forEach(key -> transactions.put(Integer.valueOf(key), new LinkedList<Transaction>()));
    }

    public void put(int key, Transaction transaction) {

        List<Transaction> transactionList = transactions.get(Integer.valueOf(key));

        synchronized (transactionList) {
            transactionList.add(transaction);
        }
    }
}
