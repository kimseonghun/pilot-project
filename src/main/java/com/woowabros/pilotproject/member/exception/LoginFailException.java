package com.woowabros.pilotproject.member.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException() {
        super("아이디나 비밀번호가 틀렸습니다.");
    }
}
