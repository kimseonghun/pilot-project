package com.woowabros.pilotproject.domain.coupon.domain;

import com.woowabros.pilotproject.domain.coupon.exception.CannotCreateCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponTest {
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(1))
                .price(1000)
                .amount(1)
                .build();
    }

    @Test
    void 발급가능날짜가_사용가능날짜보다_이후인_경우_쿠폰_생성_예외_테스트() {
        // given
        LocalDateTime issuableDate = LocalDateTime.now().plusDays(1);
        LocalDateTime usableDate = LocalDateTime.now().minusDays(1);

        // when & then
        assertThrows(CannotCreateCouponException.class, () -> Coupon.builder()
                .issuableDate(issuableDate)
                .usableDate(usableDate)
                .build());
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
    void 해당_쿠폰의_수량이_남아있는지_테스트() {
        // when & then
        assertThat(coupon.isIssuableAmount()).isTrue();
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
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(1))
                .price(1000)
                .amount(0)
                .build();

        // when & then
        assertThrows(NotIssuableCouponException.class, coupon::subtractAmount);
    }
}