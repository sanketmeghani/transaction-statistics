package dev.sanket.transactionstatistics.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Statistics {

    private double max;

    private double min;

    private double sum;

    private double avg;

    private long count;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void lockForRead() {
        this.lock.readLock().lock();
    }

    public void releaseReadLock() {
        this.lock.readLock().unlock();
    }

    public void lockForWrite() {
        this.lock.writeLock().lock();
    }

    public void releaseWriteLock() {
        this.lock.writeLock().unlock();
    }

    public void addNewTransaction(double transactionAmount) {

        this.lockForWrite();

        if (this.count == 0) {
            this.max = transactionAmount;
            this.min = transactionAmount;
        } else if (transactionAmount > this.max) {
            this.max = transactionAmount;
        } else if (transactionAmount < this.min) {
            this.min = transactionAmount;
        }
        
        this.sum = this.sum + transactionAmount;
        this.count = this.count + 1;
        this.avg = sum / count;

        this.releaseWriteLock();
    }

    public Map<String, Object> snapshot() {

        Map<String, Object> snapshot = new HashMap<>();

        this.lockForRead();

        snapshot.put("sum", Double.valueOf(this.sum));
        snapshot.put("avg", Double.valueOf(this.avg));
        snapshot.put("max", Double.valueOf(this.max));
        snapshot.put("min", Double.valueOf(this.min));
        snapshot.put("count", Long.valueOf(this.count));

        this.releaseReadLock();

        return snapshot;
    }

    public void reset() {

        this.lockForWrite();

        this.max = 0;
        this.min = 0;
        this.avg = 0;
        this.sum = 0;
        this.count = 0;

        this.releaseWriteLock();
    }

    @Override
    public String toString() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}
