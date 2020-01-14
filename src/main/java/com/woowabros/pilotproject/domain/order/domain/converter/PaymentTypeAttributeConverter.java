package com.woowabros.pilotproject.domain.order.domain.converter;

import com.woowabros.pilotproject.domain.order.domain.vo.PaymentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PaymentTypeAttributeConverter implements AttributeConverter<PaymentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentType attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentType convertToEntityAttribute(Integer dbData) {
        return PaymentType.ofCode(dbData);
    }
}
