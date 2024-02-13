package com.example.demo.token.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TokenDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String accessToken;
    }

    @Getter
    @AllArgsConstructor
    public static class All {
        private String accessToken;
        private String refreshToken;
    }
}
