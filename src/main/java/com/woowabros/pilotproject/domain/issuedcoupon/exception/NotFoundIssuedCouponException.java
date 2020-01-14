package com.woowabros.pilotproject.domain.issuedcoupon.exception;

public class NotFoundIssuedCouponException extends RuntimeException {
    public NotFoundIssuedCouponException() {
        super("해당 코드에 맞는 쿠폰이 존재하지 않습니다.");
    }
}
