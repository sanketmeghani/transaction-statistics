package dev.sanket.transactionstatistics.exception;

public class TransactionServiceException extends Exception {

    private static final long serialVersionUID = -4860883921334278347L;

    public TransactionServiceException() {
        super();
    }

    public TransactionServiceException(String message) {
        super(message);
    }

    public TransactionServiceException(String message, Throwable t) {
        super(message, t);
    }
}
