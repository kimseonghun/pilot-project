package com.woowabros.pilotproject.domain.issuedcoupon.domain;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponStatus;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotIssuableCouponException;
import com.woowabros.pilotproject.domain.issuedcoupon.exception.NotUsableCouponException;
import com.woowabros.pilotproject.domain.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void 발급_가능_상태_확인_테스트() {
        assertThat(issuedCoupon.isIssuableStatus()).isTrue();
    }

    @Test
    void 쿠폰_발급_테스트() {
        // when
        issuedCoupon.issueTo(mock(Member.class));

        // then
        assertThat(issuedCoupon.getStatus()).isEqualTo(CouponStatus.USABLE);
    }

    @Test
    void 발급_가능_상태가_아닌_경우의_쿠폰_발급_예외_테스트() {
        // given
        Member member = mock(Member.class);
        issuedCoupon = issuedCoupon.issueTo(member);

        // when & then
        assertThrows(NotIssuableCouponException.class, () -> issuedCoupon.issueTo(member));
    }

    @Test
    void 쿠폰_사용_테스트() {
        // given
        issuedCoupon = issuedCoupon.issueTo(mock(Member.class));

        // when
        IssuedCoupon response = issuedCoupon.use();

        // then
        assertThat(response.getStatus()).isEqualTo(CouponStatus.COMPLETED);
    }

    @Test
    void 사용_가능_상태가_아닌_쿠폰_사용_예외_테스트() {
        // when & then
        assertThrows(NotUsableCouponException.class, () -> issuedCoupon.use());
    }
}