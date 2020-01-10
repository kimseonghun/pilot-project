package com.woowabros.pilotproject.domain.coupon.exception;

public class NotFoundCouponException extends RuntimeException {
    public NotFoundCouponException() {
        super("헤당 쿠폰은 존재하지 않습니다.");
    }
}
