package com.woowabros.pilotproject.domain.issuedcoupon.controller;

import com.woowabros.pilotproject.config.resolver.SessionMember;
import com.woowabros.pilotproject.domain.common.domain.ErrorResponse;
import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.issuedcoupon.service.IssuedCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issuedcoupon")
public class IssuedCouponApiController {
    private final IssuedCouponService issuedCouponService;

    public IssuedCouponApiController(IssuedCouponService issuedCouponService) {
        this.issuedCouponService = issuedCouponService;
    }

    @GetMapping("/usable")
    public ResponseEntity<List<IssuedCouponResponseDto>> findUsableCouponsByMemberId(SessionMember sessionMember) {
        List<IssuedCouponResponseDto> response = issuedCouponService.findUsableCouponsByMemberId(sessionMember.getMemberId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity issue(@PathVariable Long couponId, SessionMember sessionMember) {
        issuedCouponService.issue(couponId, sessionMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
