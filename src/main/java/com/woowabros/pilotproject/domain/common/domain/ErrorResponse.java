package com.woowabros.pilotproject.domain.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private int status;

    @Builder
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
