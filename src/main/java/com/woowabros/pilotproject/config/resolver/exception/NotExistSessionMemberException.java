package com.woowabros.pilotproject.config.resolver.exception;

public class NotExistSessionMemberException extends RuntimeException {
    public NotExistSessionMemberException() {
        super("세션안에 회원 정보가 존재하지 않습니다.");
    }
}
