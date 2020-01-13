package com.woowabros.pilotproject.domain.coupon.exception;

public class CannotCreateCouponException extends RuntimeException {
    public CannotCreateCouponException() {
        super("발급 가능 날짜가 사용 가능 날짜보다 이후로 설정할 수 없습니다.");
    }
}
