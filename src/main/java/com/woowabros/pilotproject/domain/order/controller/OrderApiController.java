package com.woowabros.pilotproject.domain.order.controller;

import com.woowabros.pilotproject.config.resolver.SessionMember;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderApiController {
    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody OrderCreateRequestDto orderDto, SessionMember sessionMember) {
        orderService.create(orderDto, sessionMember.getMemberId());
        return ResponseEntity.ok().build();
    }
}
