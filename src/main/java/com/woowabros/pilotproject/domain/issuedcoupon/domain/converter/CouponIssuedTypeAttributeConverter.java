package com.woowabros.pilotproject.domain.issuedcoupon.domain.converter;

import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponIssuedType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CouponIssuedTypeAttributeConverter implements AttributeConverter<CouponIssuedType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponIssuedType attribute) {
        return attribute.getCode();
    }

    @Override
    public CouponIssuedType convertToEntityAttribute(Integer dbData) {
        return CouponIssuedType.ofCode(dbData);
    }
}
