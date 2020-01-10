package com.woowabros.pilotproject.domain.issuedcoupon.exception;

public class NotFoundCouponIssuedTypeException extends RuntimeException {
    public NotFoundCouponIssuedTypeException() {
        super("주어진 코드에 해당하는 실제 쿠폰 타입을 찾을 수 없습니다.");
    }
}
