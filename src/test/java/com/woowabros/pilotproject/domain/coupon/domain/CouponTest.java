package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponTest {
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(DateUtil.tomorrow())
                .usableDate(DateUtil.tomorrow())
                .price(1000)
                .amount(1)
                .build();
    }

    @Test
    void 해당_쿠폰이_등록가능한_날인지_테스트() {
        // when & then
        assertThat(coupon.isIssuableDate()).isTrue();
    }

    @Test
    void 해당_쿠폰이_사용가능한_날인지_테스트() {
        // when & then
        assertThat(coupon.isUsableDate()).isTrue();
    }

    @Test
    void 해당_쿠폰_수량_차감_테스트() {
        // when & then
        assertThat(coupon.subtractAmount()).isEqualTo(0);
    }

    @Test
    void 해당_쿠폰_수량_차감_예외_테스트() {
        // given
        Coupon coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(DateUtil.tomorrow())
                .usableDate(DateUtil.tomorrow())
                .price(1000)
                .amount(0)
                .build();

        // when & then
        assertThrows(NotIssuableCouponException.class, coupon::subtractAmount);
    }
}