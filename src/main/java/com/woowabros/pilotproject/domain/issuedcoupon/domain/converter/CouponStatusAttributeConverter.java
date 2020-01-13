package com.woowabros.pilotproject.domain.issuedcoupon.domain.converter;

import com.woowabros.pilotproject.domain.issuedcoupon.domain.vo.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CouponStatusAttributeConverter implements AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer dbData) {
        return CouponStatus.ofCode(dbData);
    }
}
