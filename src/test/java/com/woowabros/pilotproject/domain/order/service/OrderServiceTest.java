package com.woowabros.pilotproject.domain.order.service;

import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.order.domain.OrderRepository;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.ordermenu.service.OrderMenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private OrderMenuService orderMenuService;

    @Mock
    private IssuedCouponService issuedCouponService;

    @Test
    void 주문_내역_조회_테스트() {
        // given
        Member member = mock(Member.class);
        Order order = Order.builder()
                .payment(PaymentType.CARD)
                .totalPrice(36000)
                .member(member)
                .build();
        Long orderId = order.getId();
        List<MenuResponseDto> menus = Collections.singletonList(mock(MenuResponseDto.class));
        String paymentType = order.getPayment().getName();
        String orderStatus = order.getStatus().getName();
        List<IssuedCouponResponseDto> coupons = Collections.singletonList(mock(IssuedCouponResponseDto.class));

        given(memberService.findById(any())).willReturn(member);
        given(orderRepository.findAllByMember(any())).willReturn(Collections.singletonList(order));
        given(orderMenuService.findByOrder(any())).willReturn(menus);
        given(issuedCouponService.findUsedCouponByOrderId(any())).willReturn(coupons);

        // when
        List<OrderResponseDto> orders = orderService.findByMember(1L);

        // then
        assertThat(orders).contains(OrderResponseDto.builder()
                .orderId(orderId)
                .menus(menus)
                .paymentType(paymentType)
                .orderStatus(orderStatus)
                .coupons(coupons)
                .build());
        verify(memberService, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).findAllByMember(any());
        verify(orderMenuService, times(1)).findByOrder(any());
        verify(issuedCouponService, times(1)).findUsedCouponByOrderId(any());
    }
}