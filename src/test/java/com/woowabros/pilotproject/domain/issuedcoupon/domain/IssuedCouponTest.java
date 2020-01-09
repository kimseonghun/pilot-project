package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class IssuedCouponTest {

    @Test
    void 발급_쿠폰_동일성_테스트() {
        Coupon coupon = mock(Coupon.class);
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();

        assertThat(issuedCoupon).isEqualTo(IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build());
    }
}