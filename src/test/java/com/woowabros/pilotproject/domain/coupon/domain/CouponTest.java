package com.woowabros.pilotproject.domain.coupon.domain;

import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CouponTest {

    @Test
    void 해당_쿠폰이_등록가능한_날인지_테스트() {
        // given
        Date issuableDate = DateUtil.tomorrow();
        Coupon coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(issuableDate)
                .usableDate(mock(Date.class))
                .price(1000)
                .build();

        // when & then
        assertThat(coupon.isIssuableDate()).isTrue();
    }

    @Test
    void 해당_쿠폰이_사용가능한_날인지_테스트() {
        // given
        Date usableDate = DateUtil.tomorrow();
        Coupon coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(mock(Date.class))
                .usableDate(usableDate)
                .price(1000)
                .build();

        // when & then
        assertThat(coupon.isUsableDate()).isTrue();
    }
}