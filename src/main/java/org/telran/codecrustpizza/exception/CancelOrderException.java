package org.telran.codecrustpizza.exception;

public class CancelOrderException extends RuntimeException {

    public CancelOrderException(String message) {
        super(message);
    }
}