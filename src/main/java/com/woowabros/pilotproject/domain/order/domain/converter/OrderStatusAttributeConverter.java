package com.woowabros.pilotproject.domain.order.domain.converter;

import com.woowabros.pilotproject.domain.order.domain.vo.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderStatusAttributeConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
        return OrderStatus.ofCode(dbData);
    }
}
