package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private LocalDateTime expirationDate;

    @Builder
    public RefreshToken(String token, Member member, LocalDateTime expirationDate) {
        this.token = token;
        this.member = member;
        this.expirationDate = expirationDate;
    }
}
