package com.woowabros.pilotproject.domain.issuedcoupon.domain.vo;

import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotFoundCouponIssuedTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponStatusTest {

    @ParameterizedTest
    @EnumSource(CouponStatus.class)
    void 실제_쿠폰_타입_생성_테스트(CouponStatus couponStatus) {
        // given
        int code = couponStatus.getCode();

        // when
        CouponStatus type = CouponStatus.ofCode(code);

        // then
        assertThat(type).isEqualTo(couponStatus);
    }

    @Test
    void 실제_쿠폰_타입_생성시_에러_테스트() {
        // given
        int code = -999;

        // when & then
        assertThrows(NotFoundCouponIssuedTypeException.class, () -> CouponStatus.ofCode(code));
    }
}