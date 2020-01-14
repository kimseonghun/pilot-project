package com.woowabros.pilotproject.domain.order.dto;

import com.woowabros.pilotproject.domain.issuedcoupon.dto.IssuedCouponResponseDto;
import com.woowabros.pilotproject.domain.menu.dto.MenuResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderResponseDto {
    private Long orderId;
    private List<MenuResponseDto> menus;
    private String paymentType;
    private String orderStatus;
    private List<IssuedCouponResponseDto> coupons;

    @Builder
    public OrderResponseDto(Long orderId, List<MenuResponseDto> menus, String paymentType, String orderStatus, List<IssuedCouponResponseDto> coupons) {
        this.orderId = orderId;
        this.menus = menus;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.coupons = coupons;
    }
}
