package com.woowabros.pilotproject.domain.order.exception;

public class CannotCancelOrderException extends RuntimeException {
    public CannotCancelOrderException(String message) {
        super(message);
    }
}
