package com.woowabros.pilotproject.domain.order.dto;

import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderCreateRequestDto {
    private Long memberId;
    private List<Long> menuIds;
    private PaymentType paymentType;
    private List<String> couponCodes;

    @Builder
    public OrderCreateRequestDto(Long memberId, List<Long> menuIds, String paymentType, List<String> couponCodes) {
        this.memberId = memberId;
        this.menuIds = menuIds;
        this.paymentType = PaymentType.ofName(paymentType);
        this.couponCodes = couponCodes;
    }
}