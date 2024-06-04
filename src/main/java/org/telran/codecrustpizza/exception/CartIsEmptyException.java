package org.telran.codecrustpizza.exception;

public class CartIsEmptyException extends RuntimeException {

    public CartIsEmptyException(String message) {
        super(message);
    }
}