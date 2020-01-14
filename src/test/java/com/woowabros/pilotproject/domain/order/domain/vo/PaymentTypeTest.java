package com.woowabros.pilotproject.domain.order.domain.vo;

import com.woowabros.pilotproject.domain.order.exception.NotFoundPaymentTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTypeTest {

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void 주문_수단_생성_테스트(PaymentType payment) {
        // given
        int code = payment.getCode();

        // when
        PaymentType paymentType = PaymentType.ofCode(code);

        // then
        assertThat(paymentType).isEqualTo(payment);
    }

    @Test
    void 주문_수단_생성_시_예외_테스트() {
        // given
        int code = -999;

        // when & then
        assertThrows(NotFoundPaymentTypeException.class, () -> PaymentType.ofCode(code));
    }
}