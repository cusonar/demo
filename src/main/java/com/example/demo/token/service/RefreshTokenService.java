package com.example.demo.token.service;

import com.example.demo.common.exception.InvalidTokenException;
import com.example.demo.domain.Member;
import com.example.demo.domain.RefreshToken;
import com.example.demo.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long save(String token, Member member, Date expirationDate) {
        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(token)
                .member(member)
                .expirationDate(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()))
                .build();
        RefreshToken saved = refreshTokenRepository.save(newRefreshToken);
        return saved.getId();
    }

    public boolean exist(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(InvalidTokenException::new);
        return true;
    }

    @Transactional
    public void deleteToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(InvalidTokenException::new);
        refreshTokenRepository.delete(refreshToken);
    }
}
