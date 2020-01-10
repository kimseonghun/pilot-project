package com.woowabros.pilotproject.domain.member.exception;

public class NotFoundMemberException extends RuntimeException {
    public NotFoundMemberException() {
        super("해당 회원은 존재하지 않습니다.");
    }
}
