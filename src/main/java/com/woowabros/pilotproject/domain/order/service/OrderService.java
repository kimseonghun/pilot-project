package com.woowabros.pilotproject.domain.order.service;

import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.member.service.MemberService;
import com.woowabros.pilotproject.domain.order.domain.Order;
import com.woowabros.pilotproject.domain.order.domain.OrderRepository;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.ordermenu.service.OrderMenuService;
import org.springframework.stereotype.Service;

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

    public List<OrderResponseDto> findByMember(Long memberId) {
        Member member = memberService.findById(memberId);

        return orderRepository.findAllByMember(member).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
