package com.woowabros.pilotproject.domain.order.domain.vo;

import com.woowabros.pilotproject.domain.order.exception.NotFoundOrderStatusException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    COMPLETED("주문 완료", 1),
    CANCEL("주문 취소", 2);

    private final String name;
    private final int code;

    OrderStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static OrderStatus ofCode(int code) {
        return Arrays.stream(values())
                .filter(status -> status.getCode() == code)
                .findAny()
                .orElseThrow(NotFoundOrderStatusException::new);
    }
}
