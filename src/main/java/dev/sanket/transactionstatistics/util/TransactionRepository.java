package dev.sanket.transactionstatistics.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import dev.sanket.transactionstatistics.model.Transaction;
import dev.sanket.transactionstatistics.service.StatisticsService;

public class TransactionRepository {

    @Autowired
    private StatisticsService statisticsService;

    private ConcurrentHashMap<Integer, List<Transaction>> transactions = new ConcurrentHashMap<Integer, List<Transaction>>();

    public TransactionRepository() {
        IntStream.range(0, 60).forEach(key -> transactions.put(Integer.valueOf(key), new LinkedList<Transaction>()));
    }

    public void put(int key, Transaction transaction) {

        List<Transaction> transactionList = transactions.get(Integer.valueOf(key));

        synchronized (transactionList) {
            transactionList.add(transaction);
            statisticsService.trackTransaction(transaction);
        }
    }
}
