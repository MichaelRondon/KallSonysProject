package edu.aes.pica.asperisk.product.service.exceptions;

public class ProductTransactionException extends Exception {

    public ProductTransactionException() {
    }

    public ProductTransactionException(String message) {
        super(message);
    }

    public ProductTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductTransactionException(Throwable cause) {
        super(cause);
    }

    public ProductTransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
