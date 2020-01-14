package com.woowabros.pilotproject.domain.order.domain.converter;

import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusAttributeConverterTest {
    private OrderStatusAttributeConverter converter = new OrderStatusAttributeConverter();

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void DB_칼럼_타입으로_변환_테스트(OrderStatus status) {
        // given
        int code = status.getCode();

        // when & then
        assertThat(converter.convertToDatabaseColumn(status)).isEqualTo(code);
    }

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void 클래스_필드_타입으로_변환_테스트(OrderStatus status) {
        // given
        OrderStatus orderStatus = converter.convertToEntityAttribute(status.getCode());

        // when & then
        assertThat(orderStatus).isEqualTo(status);
    }
}