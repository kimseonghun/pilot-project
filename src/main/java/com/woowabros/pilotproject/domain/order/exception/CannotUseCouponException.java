package com.woowabros.pilotproject.domain.order.exception;

public class CannotUseCouponException extends RuntimeException {
    public CannotUseCouponException() {
        super("주문 금액보다 할인 금액이 클 수 없습니다.");
    }
}
