package com.woowabros.pilotproject.domain.issuedcoupon.domain.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CouponCodeTest {

    @Test
    void 쿠폰_코드_동일성_테스트() {
        // given
        CouponCode couponCode = CouponCode.of("1234");

        // when & then
        assertThat(couponCode).isEqualTo(CouponCode.of("1234"));
    }

    @Test
    void 쿠폰_코드_포맷_테스트() {
        // given
        String code = "1234-1234";

        // when
        CouponCode couponCode = CouponCode.of(code);

        // then
        assertThat(couponCode.getCode()).isEqualTo("12341234");
    }
}