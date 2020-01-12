package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponIssuedType;
import com.woowabros.pilotproject.domain.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class IssuedCouponTest {
    private IssuedCoupon issuedCoupon;

    @BeforeEach
    void setUp() {
        issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(mock(Coupon.class))
                .build();
    }

    @Test
    void 발급_쿠폰_동일성_테스트() {
        // given
        Coupon coupon = mock(Coupon.class);
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();

        // when & then
        assertThat(issuedCoupon).isEqualTo(IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build());
    }

    @Test
    void 발급_상태_확인_테스트() {
        assertThat(issuedCoupon.isIssuableStatus()).isTrue();
    }

    @Test
    void 쿠폰_발급_테스트() {
        // when
        issuedCoupon.issueTo(mock(Member.class));

        // then
        assertThat(issuedCoupon.getStatus()).isEqualTo(CouponIssuedType.USABLE);
    }
}