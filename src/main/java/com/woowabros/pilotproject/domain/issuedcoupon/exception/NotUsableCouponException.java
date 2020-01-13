package com.woowabros.pilotproject.domain.issuedcoupon.exception;

public class NotUsableCouponException extends RuntimeException {
    public NotUsableCouponException() {
        super("해당 쿠폰을 사용할 수 없습니다.");
    }
}
