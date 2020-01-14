package com.woowabros.pilotproject.domain.order.exception;

public class NotFoundOrderException extends RuntimeException {
    public NotFoundOrderException() {
        super("해당 주문은 존재하지 않습니다.");
    }
}
