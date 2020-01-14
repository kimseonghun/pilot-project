package com.woowabros.pilotproject.domain.order.domain.converter;

import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTypeAttributeConverterTest {
    PaymentTypeAttributeConverter converter = new PaymentTypeAttributeConverter();

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void DB_칼럼_타입으로_변환_테스트(PaymentType status) {
        // given
        int code = status.getCode();

        // when & then
        assertThat(converter.convertToDatabaseColumn(status)).isEqualTo(code);
    }

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void 클래스_필드_타입으로_변환_테스트(PaymentType status) {
        // given
        PaymentType paymentType = converter.convertToEntityAttribute(status.getCode());

        // when & then
        assertThat(paymentType).isEqualTo(status);
    }

}