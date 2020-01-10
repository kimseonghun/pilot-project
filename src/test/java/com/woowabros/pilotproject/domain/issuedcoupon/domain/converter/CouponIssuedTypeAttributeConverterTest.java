package com.woowabros.pilotproject.domain.issuedcoupon.domain.converter;

import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponIssuedType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class CouponIssuedTypeAttributeConverterTest {
    private CouponIssuedTypeAttributeConverter converter = new CouponIssuedTypeAttributeConverter();

    @ParameterizedTest
    @EnumSource(CouponIssuedType.class)
    void DB_칼럼_타입으로_변환_테스트(CouponIssuedType couponIssuedType) {
        // given
        int code = couponIssuedType.getCode();

        // when & then
        assertThat(converter.convertToDatabaseColumn(couponIssuedType)).isEqualTo(code);
    }

    @ParameterizedTest
    @EnumSource(CouponIssuedType.class)
    void 클래스_필드_타입으로_변환_테스트(CouponIssuedType couponIssuedType) {
        // given
        CouponIssuedType type = converter.convertToEntityAttribute(couponIssuedType.getCode());

        // when & then
        assertThat(type).isEqualTo(couponIssuedType);
    }
}