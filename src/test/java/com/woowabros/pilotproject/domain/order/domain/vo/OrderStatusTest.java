package com.woowabros.pilotproject.domain.order.domain.vo;

import com.woowabros.pilotproject.domain.order.exception.NotFoundOrderStatusException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderStatusTest {

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void 주문_상태_생성_테스트(OrderStatus status) {
        // given
        int code = status.getCode();

        // when
        OrderStatus orderStatus = OrderStatus.ofCode(code);

        // then
        assertThat(orderStatus).isEqualTo(status);
    }

    @Test
    void 주문_상태_생성_시_예외_테스트() {
        // given
        int code = -999;

        // when & then
        assertThrows(NotFoundOrderStatusException.class, () -> OrderStatus.ofCode(code));
    }
}