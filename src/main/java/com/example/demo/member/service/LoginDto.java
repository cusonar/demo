package com.example.demo.member.service;

import lombok.Getter;
import lombok.ToString;

public class LoginDto {

    @Getter
    @ToString
    public static class Request {
        private String username;
        private String password;
    }
}
