package com.example.demo.domain;

import com.example.demo.hello.service.HelloDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Hello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @Builder
    public Hello(String message) {
        this.message = message;
    }

    public void update(HelloDto.Update dto) {
        if (dto.getMessage() != null) this.message = dto.getMessage();
    }
}
