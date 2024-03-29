package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListResponse<T> {
    private Long count;
    private T data;
}
