package com.woowabros.pilotproject.domain.order.service;

import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.order.domain.OrderRepository;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.ordermenu.service.OrderMenuService;
import org.junit.jupiter.api.BeforeEach;
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

    private Member member;
    private Order order;

    @BeforeEach
    void setUp() {
        member = mock(Member.class);

        order = Order.builder()
                .payment(PaymentType.CARD)
                .totalPrice(36000)
                .member(member)
                .build();
    }

    @Test
    void 주문_내역_조회_테스트() {
        // given
        List<MenuResponseDto> menus = Collections.singletonList(mock(MenuResponseDto.class));
        List<IssuedCouponResponseDto> coupons = Collections.singletonList(mock(IssuedCouponResponseDto.class));

        given(memberService.findById(any())).willReturn(member);
        given(orderRepository.findAllByMember(any())).willReturn(Collections.singletonList(order));
        given(orderMenuService.findByOrder(any())).willReturn(menus);
        given(issuedCouponService.findUsedCouponByOrderId(any())).willReturn(coupons);

        // when
        List<OrderResponseDto> orders = orderService.findByMember(1L);

        // then
        assertThat(orders).contains(OrderResponseDto.builder()
                .orderId(order.getId())
                .menus(menus)
                .paymentType(order.getPayment().getName())
                .orderStatus(order.getStatus().getName())
                .coupons(coupons)
                .build());
        verify(memberService, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).findAllByMember(any());
        verify(orderMenuService, times(1)).findByOrder(any());
        verify(issuedCouponService, times(1)).findUsedCouponByOrderId(any());
    }

    @Test
    void 주문_저장_테스트() {
        // given
        Menu menu = mock(Menu.class);
        OrderCreateRequestDto dto = mock(OrderCreateRequestDto.class);

        given(memberService.findById(anyLong())).willReturn(member);

        // when
        Order response = orderService.save(dto);

        // then
        assertThat(response).isEqualTo(Order.builder()
                .member(member)
                .payment(dto.getPaymentType())
                .totalPrice(menu.getPrice())
                .build());
    }
}