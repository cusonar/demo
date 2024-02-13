package com.example.demo.member.service;

import com.example.demo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JoinDto {

    @Getter
    @ToString
    public static class Request {
        private String username;
        private String password;

        public Member toEntity() {
            PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            String encoded = passwordEncoder.encode(password);
            return Member.builder()
                    .username(username)
                    .password(encoded)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
    }
}
