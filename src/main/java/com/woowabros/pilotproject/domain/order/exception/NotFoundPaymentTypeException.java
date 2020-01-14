package com.woowabros.pilotproject.domain.order.exception;

public class NotFoundPaymentTypeException extends RuntimeException {
    public NotFoundPaymentTypeException() {
        super("주어진 코드에 해당하는 결제 수단을 찾을 수 없습니다.");
    }
}
