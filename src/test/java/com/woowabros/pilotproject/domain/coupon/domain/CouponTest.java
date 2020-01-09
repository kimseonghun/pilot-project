package com.woowabros.pilotproject.domain.coupon.domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CouponTest {

    @Test
    void 해당_쿠폰이_등록가능한_날인지_테스트() {
        // given
        Date issuableDate = getConstraintsDate();
        Coupon coupon = Coupon.builder()
                .issuableDate(issuableDate)
                .usableDate(mock(Date.class))
                .price(1000)
                .build();

        // when & then
        assertThat(coupon.isPossibleToRegister()).isTrue();
    }

    @Test
    void 해당_쿠폰이_사용가능한_날인지_테스트() {
        // given
        Date usableDate = getConstraintsDate();
        Coupon coupon = Coupon.builder()
                .issuableDate(mock(Date.class))
                .usableDate(usableDate)
                .price(1000)
                .build();

        // when & then
        assertThat(coupon.isPossibleToUse()).isTrue();
    }

    @Test
    void 쿠폰_금액_테스트() {
        // given
        int price = 1000;
        Coupon coupon = Coupon.builder()
                .issuableDate(mock(Date.class))
                .usableDate(mock(Date.class))
                .price(price)
                .build();

        // when & then
        assertThat(coupon.getPrice()).isEqualTo(price);
    }
  
    private Date getConstraintsDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.DECEMBER, 31);
        return calendar.getTime();
    }
}