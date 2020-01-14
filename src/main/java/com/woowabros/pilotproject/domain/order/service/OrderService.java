package com.woowabros.pilotproject.domain.order.service;

import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.order.domain.OrderRepository;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.order.exception.NotFoundOrderException;
import com.woowabros.pilotproject.domain.ordermenu.domain.OrderMenu;
import com.woowabros.pilotproject.domain.ordermenu.service.OrderMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final OrderMenuService orderMenuService;
    private final IssuedCouponService issuedCouponService;

    public OrderService(OrderRepository orderRepository, MemberService memberService, OrderMenuService orderMenuService, IssuedCouponService issuedCouponService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.orderMenuService = orderMenuService;
        this.issuedCouponService = issuedCouponService;
    }

    @Transactional
    public Order save(OrderCreateRequestDto orderDto) {
        Member member = memberService.findById(orderDto.getMemberId());

        Order order = Order.builder()
                .payment(orderDto.getPaymentType())
                .member(member)
                .build();

        List<OrderMenu> orderMenus = orderDto.getMenuIds().stream()
                .map(menuId -> orderMenuService.save(order, menuId))
                .collect(Collectors.toList());

        order.calculateTotalPrice(orderMenus);
        orderDto.getCouponCodes()
                .forEach(couponCode -> issuedCouponService.useCoupons(couponCode, order));

        return order;
    }

    public List<OrderResponseDto> findByMember(Long memberId) {
        Member member = memberService.findById(memberId);

        return orderRepository.findAllByMember(member).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Order cancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);

        return order.cancel();
    }

    private OrderResponseDto toDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .paymentType(order.getPayment().getName())
                .orderStatus(order.getStatus().getName())
                .menus(orderMenuService.findByOrder(order))
                .coupons(issuedCouponService.findUsedCouponByOrderId(order))
                .build();
    }
}
