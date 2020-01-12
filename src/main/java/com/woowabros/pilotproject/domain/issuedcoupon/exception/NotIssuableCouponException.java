package com.woowabros.pilotproject.domain.issuedcoupon.exception;

public class NotIssuableCouponException extends RuntimeException {
    public NotIssuableCouponException() {
        super("해당 쿠폰은 발급할 수 없습니다.");
    }
}
