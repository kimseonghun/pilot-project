package com.woowabros.pilotproject.domain.issuedcoupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.service.CouponInnerService;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCouponRepository;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssuedCouponServiceTest {

    @InjectMocks
    private IssuedCouponService issuedCouponService;

    @Mock
    private IssuedCouponRepository issuedCouponRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private CouponInnerService couponInnerService;

    @Test
    void 발급_쿠폰_생성_테스트() {
        // given
        Coupon coupon = mock(Coupon.class);
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();
        given(issuedCouponRepository.save(any())).willReturn(issuedCoupon);

        // when
        IssuedCoupon result = issuedCouponService.create(coupon);

        // then
        assertThat(result).isEqualTo(issuedCoupon);
        verify(issuedCouponRepository, times(1)).save(any());
    }

    @Test
    void 쿠폰_발급_테스트() {
        // given
        Member member = mock(Member.class);
        Coupon coupon = Coupon.builder()
                .issuableDate(DateUtil.tomorrow())
                .usableDate(DateUtil.tomorrow())
                .price(3000)
                .amount(10)
                .build();
        createIssuedCoupon(coupon);

        given(memberService.findById(anyLong())).willReturn(member);
        given(couponInnerService.findUsableCouponById(anyLong())).willReturn(coupon);
        given(issuedCouponRepository.findAllByMember(any())).willReturn(Collections.singletonList(mock(IssuedCoupon.class)));

        // when
        IssuedCoupon response = issuedCouponService.issue(1L, 1L);

        // then
        assertThat(response.getMember()).isEqualTo(member);
    }

    private void createIssuedCoupon(Coupon coupon) {
        IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();
    }
}