package com.example.demo.common.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("유효한 토큰이 아닙니다.");
    }
}
