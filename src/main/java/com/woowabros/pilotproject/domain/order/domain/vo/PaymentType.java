package com.woowabros.pilotproject.domain.order.domain.vo;

import com.woowabros.pilotproject.domain.order.exception.NotFoundPaymentTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentType {
    CARD("카드 결제", 1),
    PHONE("핸드폰 결제", 2),
    SIMPLICITY("간편 결제", 3);

    private final String name;
    private final int code;

    PaymentType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static PaymentType ofCode(int code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode() == code)
                .findAny()
                .orElseThrow(NotFoundPaymentTypeException::new);
    }
}
