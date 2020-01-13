package com.woowabros.pilotproject.domain.issuedcoupon.domain.vo;

import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotFoundCouponIssuedTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CouponStatus {
    ISSUABLE("발급 가능", 1),
    USABLE("사용 가능", 2),
    COMPLETED("사용 완료", 3);

    private final String name;
    private final int code;

    CouponStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static CouponStatus ofCode(int code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode() == code)
                .findAny()
                .orElseThrow(NotFoundCouponIssuedTypeException::new);
    }
}
