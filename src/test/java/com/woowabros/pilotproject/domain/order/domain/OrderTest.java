package com.woowabros.pilotproject.domain.order.domain;

import com.woowabros.pilotproject.domain.member.domain.Member;
import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OrderTest {

    @Test
    void 주문_생성_테스트() {
        // given
        Order order = Order.builder()
                .payment(PaymentType.CARD)
                .member(mock(Member.class))
                .build();

        // when & then
        assertThat(order).isEqualTo(Order.builder()
                .payment(PaymentType.CARD)
                .member(mock(Member.class))
                .build());
    }
}