package com.woowabros.pilotproject.domain.order.controller;

import com.woowabros.pilotproject.config.resolver.SessionMember;
import com.woowabros.pilotproject.domain.common.domain.ErrorResponse;
import com.woowabros.pilotproject.domain.order.dto.OrderCreateRequestDto;
import com.woowabros.pilotproject.domain.order.dto.OrderResponseDto;
import com.woowabros.pilotproject.domain.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{orderId}")
    public ResponseEntity cancel(@PathVariable Long orderId, SessionMember sessionMember) {
        orderService.cancel(orderId, sessionMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll(SessionMember sessionMember) {
        List<OrderResponseDto> response = orderService.findOrdersByMemberId(sessionMember.getMemberId());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
