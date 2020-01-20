package com.woowabros.pilotproject.domain.order.service;

import com.woowabros.pilotproject.domain.coupon.domain.Coupon;
import com.woowabros.pilotproject.domain.issuedcoupon.domain.IssuedCoupon;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.menu.domain.Menu;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.order.domain.OrderRepository;
import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import com.woowabros.pilotproject.domain.ordermenu.service.OrderMenuService;
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
    private List<MenuResponseDto> menus;
    private List<IssuedCouponResponseDto> coupons;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        member = mock(Member.class);

        order = Order.builder()
                .payment(PaymentType.CARD)
                .totalPrice(36000)
                .member(member)
                .build();

        menus = Collections.singletonList(mock(MenuResponseDto.class));
        coupons = Collections.singletonList(mock(IssuedCouponResponseDto.class));

        orderResponseDto = OrderResponseDto.builder()
                .orderId(order.getId())
                .menus(menus)
                .paymentType(order.getPayment().getName())
                .orderStatus(order.getStatus().getName())
                .coupons(coupons)
                .totalPrice(order.getTotalPrice())
                .build();
    }

    @Test
    void 주문_저장_테스트() {
        // given
        Menu menu = mock(Menu.class);
        OrderMenu orderMenu = OrderMenu.builder()
                .order(order)
                .menu(menu)
                .build();
        Coupon coupon = Coupon.builder()
                .issuableDate(LocalDateTime.now())
                .usableDate(LocalDateTime.now())
                .price(3000).build();
        IssuedCoupon issuedCoupon = IssuedCoupon.builder()
                .couponCode("1234")
                .coupon(coupon)
                .build();
        List<String> couponCodes = Collections.singletonList("1234");
        List<Long> menuIds = Collections.singletonList(1L);
        OrderCreateRequestDto dto = OrderCreateRequestDto.builder()
                .couponCodes(couponCodes)
                .menuIds(menuIds)
                .paymentType(PaymentType.SIMPLICITY.getName())
                .build();

        given(orderRepository.save(any())).willReturn(Order.builder()
                .payment(PaymentType.SIMPLICITY)
                .member(member)
                .build());
        given(memberService.findById(anyLong())).willReturn(member);
        given(orderMenuService.save(any(), anyLong())).willReturn(orderMenu);
        given(issuedCouponService.useCoupons(anyString(), any())).willReturn(issuedCoupon);

        // when
        Order result = orderService.create(dto, 1L);

        // then
        assertThat(result).isEqualTo(Order.builder()
                .member(member)
                .payment(dto.getPaymentType())
                .totalPrice(menu.getPrice())
                .totalDiscountPrice(coupon.getPrice())
                .build());
        verify(memberService, times(1)).findById(anyLong());
        verify(orderMenuService, times(menuIds.size())).save(any(), anyLong());
        verify(issuedCouponService, times(couponCodes.size())).useCoupons(anyString(), any());
    }

    @Test
    void 주문_내역_조회_테스트() {
        // given
        given(memberService.findById(any())).willReturn(member);
        given(orderRepository.findAllByMember(any())).willReturn(Collections.singletonList(order));
        given(orderMenuService.findByOrder(any())).willReturn(menus);
        given(issuedCouponService.findUsedCouponByOrderId(any())).willReturn(coupons);

        // when
        List<OrderResponseDto> orders = orderService.findOrdersByMemberId(1L);

        // then
        assertThat(orders).contains(orderResponseDto);
        verify(memberService, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).findAllByMember(any());
        verify(orderMenuService, times(1)).findByOrder(any());
        verify(issuedCouponService, times(1)).findUsedCouponByOrderId(any());
    }

    @Test
    void 주문_단건_조회() {
        // given
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(orderMenuService.findByOrder(any())).willReturn(menus);
        given(issuedCouponService.findUsedCouponByOrderId(any())).willReturn(coupons);

        // when
        OrderResponseDto result = orderService.findOrderById(1L);

        // then
        assertThat(result).isEqualTo(orderResponseDto);
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderMenuService, times(1)).findByOrder(any());
        verify(issuedCouponService, times(1)).findUsedCouponByOrderId(any());
    }

    @Test
    void 주문_취소_테스트() {
        // given
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));

        // when
        Order result = orderService.cancel(1L);

        // then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCEL);
        verify(orderRepository, times(1)).findById(anyLong());
    }
}