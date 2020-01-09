package com.woowabros.pilotproject.domain.issuedcoupon.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponCode {
    private String code;

    private CouponCode(String code) {
        this.code = format(code);
    }

    public static CouponCode of(String code) {
        return new CouponCode(code);
    }

    private String format(String code) {
        return code.replaceAll("-", "").toUpperCase();
    }
}

