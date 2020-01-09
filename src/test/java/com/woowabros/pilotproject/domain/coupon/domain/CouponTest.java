package com.woowabros.pilotproject.domain.coupon.domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class CouponTest {

    @Test
    void 해당_쿠폰이_등록가능한_날인지_테스트() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.DECEMBER, 31);
        Date registrableDate = calendar.getTime();
        Coupon coupon = Coupon.builder()
                .registrableDate(registrableDate)
                .build();

        // when & then
        assertThat(coupon.isPossibleToRegister()).isTrue();
    }
}