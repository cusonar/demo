package com.example.demo.hello.service;

import com.example.demo.domain.Hello;
import lombok.*;

public class HelloDto {

    @Getter
    @Builder
    public static class List {
        private Long id;
        private String message;

        public static List of(Hello hello) {
            return builder()
                    .id(hello.getId())
                    .message(hello.getMessage())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Detail {
        private Long id;
        private String message;

        public static Detail of(Hello hello) {
            return builder()
                    .id(hello.getId())
                    .message(hello.getMessage())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Create {
        private String message;

        public Hello toEntity() {
            return Hello.builder()
                    .message(message)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        private String message;
    }
}
