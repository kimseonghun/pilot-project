package com.woowabros.pilotproject.domain.issuedcoupon.domain.converter;

import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class CouponStatusAttributeConverterTest {
    private CouponStatusAttributeConverter converter = new CouponStatusAttributeConverter();

    @ParameterizedTest
    @EnumSource(CouponStatus.class)
    void DB_칼럼_타입으로_변환_테스트(CouponStatus couponStatus) {
        // given
        int code = couponStatus.getCode();

        // when & then
        assertThat(converter.convertToDatabaseColumn(couponStatus)).isEqualTo(code);
    }

    @ParameterizedTest
    @EnumSource(CouponStatus.class)
    void 클래스_필드_타입으로_변환_테스트(CouponStatus couponStatus) {
        // given
        CouponStatus type = converter.convertToEntityAttribute(couponStatus.getCode());

        // when & then
        assertThat(type).isEqualTo(couponStatus);
    }
}