package com.woowabros.pilotproject.domain.menu.exception;

public class NotFoundMenuException extends RuntimeException {
    public NotFoundMenuException() {
        super("해당 메뉴는 존재하지 않습니다.");
    }
}
