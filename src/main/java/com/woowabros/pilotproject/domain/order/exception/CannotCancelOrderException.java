package com.woowabros.pilotproject.domain.order.exception;

public class CannotCancelOrderException extends RuntimeException {
    public CannotCancelOrderException() {
        super("이미 취소된 주문입니다.");
    }
}
