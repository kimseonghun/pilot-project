package com.woowabros.pilotproject.member.exception;

public class NotValidMemberCreateException extends RuntimeException {
    public NotValidMemberCreateException() {
        super("회원 생성 시 값이 공백이면 안됩니다.");
    }
}
