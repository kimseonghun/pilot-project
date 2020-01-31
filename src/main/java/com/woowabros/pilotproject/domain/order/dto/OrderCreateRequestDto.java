package com.woowabros.pilotproject.domain.order.dto;

import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderCreateRequestDto {

    private List<Long> menuIds;
    private PaymentType paymentType;
    private List<String> couponCodes;

    @Builder
    public OrderCreateRequestDto(List<Long> menuIds, PaymentType paymentType, List<String> couponCodes) {
        this.menuIds = menuIds;
        this.paymentType = paymentType;
        this.couponCodes = couponCodes;
    }
}
