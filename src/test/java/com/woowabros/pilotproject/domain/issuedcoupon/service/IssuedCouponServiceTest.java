package com.woowabros.pilotproject.domain.issuedcoupon.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.coupon.service.CouponInnerService;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCouponRepository;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private Coupon coupon;
    private IssuedCoupon issuedCoupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
                .name("주마회가 쏜다")
                .issuableDate(LocalDateTime.now().plusDays(1))
                .usableDate(LocalDateTime.now().plusDays(1))
                .price(3000)
                .amount(10)
                .build();

        issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();
    }

    @Test
    void 발급_쿠폰_생성_테스트() {
        // given
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
        given(memberService.findById(anyLong())).willReturn(member);
        given(couponInnerService.findUsableCouponById(anyLong())).willReturn(coupon);
        given(issuedCouponRepository.findAllByMember(any())).willReturn(Collections.singletonList(mock(IssuedCoupon.class)));

        // when
        IssuedCoupon result = issuedCouponService.issue(1L, 1L);

        // then
        assertThat(result.getMember()).isEqualTo(member);
        verify(memberService, times(1)).findById(anyLong());
        verify(couponInnerService, times(1)).findUsableCouponById(anyLong());
        verify(issuedCouponRepository, times(1)).findAllByMember(any());
    }

    @Test
    void 주문_시_사용가능_쿠폰_조회_테스트() {
        // given
        Member member = mock(Member.class);
        issuedCoupon.issueTo(member);
        given(memberService.findById(anyLong())).willReturn(member);
        given(issuedCouponRepository.findAllByMember(any())).willReturn(Collections.singletonList(issuedCoupon));

        // when
        List<IssuedCouponResponseDto> result = issuedCouponService.findUsableCouponsByMemberId(1L);

        // then
        assertThat(result).contains(IssuedCouponResponseDto.builder()
                .couponCode(issuedCoupon.getCouponCode().getCode())
                .couponName(issuedCoupon.getCoupon().getName())
                .couponPrice(issuedCoupon.getCoupon().getPrice())
                .build());
        verify(memberService, times(1)).findById(anyLong());
        verify(issuedCouponRepository, times(1)).findAllByMember(any());
    }

    @Test
    void 해당_주문에서_사용한_쿠폰_리스트_조회() {
        // given
        given(issuedCouponRepository.findAllByOrder(any())).willReturn(Collections.singletonList(issuedCoupon));

        // when
        List<IssuedCouponResponseDto> result = issuedCouponService.findUsedCouponByOrderId(mock(Order.class));

        // then
        assertThat(result).contains(IssuedCouponResponseDto.builder()
                .couponCode(issuedCoupon.getCouponCode().getCode())
                .couponName(issuedCoupon.getCoupon().getName())
                .couponPrice(issuedCoupon.getCoupon().getPrice())
                .build());
        verify(issuedCouponRepository, times(1)).findAllByOrder(any());
    }

    @Test
    void 쿠폰_사용_테스트() {
        // given
        Order order = mock(Order.class);
        given(issuedCouponRepository.findByCouponCode(any())).willReturn(Optional.of(issuedCoupon.issueTo(mock(Member.class))));

        // when
        IssuedCoupon result = issuedCouponService.useCoupons("1234", order);

        // then
        assertThat(result.getOrder()).isEqualTo(order);
        verify(issuedCouponRepository, times(1)).findByCouponCode(any());
    }
}