package com.woowabros.pilotproject.domain.order.exception;

public class NotFoundOrderStatusException extends RuntimeException {
    public NotFoundOrderStatusException() {
        super("주어진 코드에 해당하는 주문 상태를 찾을 수 없습니다.");
    }
}
